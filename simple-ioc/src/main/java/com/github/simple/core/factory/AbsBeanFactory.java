package com.github.simple.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.*;
import com.github.simple.core.aop.SimpleAdviseSupport;
import com.github.simple.core.aop.SimpleAutoProxyCreator;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.enums.SimpleIOCEnum;
import com.github.simple.core.exception.SimpleBeanCreateException;
import com.github.simple.core.exception.SimpleClassNotFoundException;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:29 下午
 * @description: AbsBeanFactory
 */
@Slf4j
public abstract class AbsBeanFactory extends SimpleDefaultSingletonBeanRegistry implements SimpleBeanFactory {


    /**
     * bean 定义
     */
    protected final Map<String, SimpleRootBeanDefinition> beanDefinitions = new ConcurrentHashMap<>(128);

    /**
     * bean后置处理器
     */
    protected List<SimpleBeanPostProcessor> simplePostProcessors = new ArrayList<>(128);

    @Override
    public <T> T getBean(Class<?> clazz) throws Throwable {
        return doGetBean(clazz.getSimpleName());
    }

    /**
     * 获取bean
     *
     * @param name
     * @return
     * @throws Throwable
     */
    protected <T> T doGetBean(String name) throws Throwable {
        String beanName = transformName(name);
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return (T) singleton;
        }
        SimpleRootBeanDefinition sbf = getMergeBeanDefinition(beanName);
        return getSingletonBean(beanName, () -> {
                    try {
                        return createBean(beanName, sbf);
                    } catch (Exception e) {
                        singletonObjectMap.clear();
                        throw new SimpleBeanCreateException(SimpleIOCEnum.CREATE_BEAN_ERROR.getMsg());
                    }
                }
        );
    }

    /**
     * @param beanName
     * @return beanDefinition
     * @description: 简单获取bean定义
     */
    private SimpleRootBeanDefinition getMergeBeanDefinition(String beanName) {
        return beanDefinitions.get(beanName);
    }

    protected abstract Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable;

    /**
     * @param name
     * @return beanName
     * @description: 转换beanName
     */
    private String transformName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new SimpleClassNotFoundException(SimpleIOCEnum.CLASS_TYPE_NOT_NULL.getMsg());
        }
        if (name.length() >= 1 && name.toCharArray()[0] >= 97 && name.toCharArray()[0] <= 122) {
            return name;
        }
        return ClassUtils.toLowerBeanName(name);
    }

    @Override
    public <T> T getBean(String name) throws Throwable {
        return doGetBean(name);
    }


    public void addBeanDefinition(String beanName, SimpleRootBeanDefinition rootBeanDefinition) {
        if (!this.beanDefinitions.containsKey(beanName)) {
            synchronized (this.beanDefinitions) {
                this.beanDefinitions.put(beanName, rootBeanDefinition);
            }
        }
//        System.out.println("=====>>>beanDefinitionMap size:" + beanDefinitionMap.size());
    }

    /**
     * @param finalInstance
     * @param wrapperInstance
     * @return obj 代理对象&本对象
     */
    protected Object earlySingleton(String beanName, Object wrapperInstance) {
        //spring 此处为依赖注入检查bean是否为代理类,如果是则替换为代理类实现依赖注入
        Object exportObject = wrapperInstance;
        for (SimpleBeanPostProcessor simpleBeanPostProcessor : getBeanPostProcessor()) {
            if (simpleBeanPostProcessor instanceof SimpleSmartInstantiationAwareBeanPostProcessor) {
                SimpleSmartInstantiationAwareBeanPostProcessor smart = (SimpleSmartInstantiationAwareBeanPostProcessor) simpleBeanPostProcessor;
                exportObject = smart.getEarlyBeanReference(wrapperInstance, beanName);
            }
        }
        return exportObject;
    }

    public void doRegistryBeanDefinition(Set<Class<?>> classSet) {
        SimpleAutoProxyCreator spc = new SimpleAutoProxyCreator();
        List<SimpleAdviseSupport> simpleAdviseSupports = new ArrayList<>();
        classSet.forEach(cb -> {
            if (ReflectUtils.matchAnnotationComponent(cb)) {
                SimpleRootBeanDefinition rootBeanDefinition = buildRootBeanDefinition(cb);
                addBeanDefinition(rootBeanDefinition.getBeanName(), rootBeanDefinition);
                parseAspect(cb, simpleAdviseSupports);
            }
        });
        spc.setSimpleAdviseSupports(simpleAdviseSupports);
        simplePostProcessors.add(spc);
    }

    private void parseAspect(Class<?> clazz, List<SimpleAdviseSupport> simpleAdviseSupports) {
        if (ReflectUtils.matchAspect(clazz)) {
            List<SimpleAdviseSupport.MethodWrapper> methods = new ArrayList<>(3);
            Map<Method, SimplePointCut> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimplePointCut.class);
            if (CollectionUtil.isNotEmpty(methodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(methodAndAnnotation);
                methods.add(methodWrapper);
            }
            Map<Method, SimpleBefore> beforeMethodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimpleBefore.class);
            if (CollectionUtil.isNotEmpty(beforeMethodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(beforeMethodAndAnnotation);
                methods.add(methodWrapper);
            }
            Map<Method, SimpleAfter> afterMethodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimpleAfter.class);
            if (CollectionUtil.isNotEmpty(afterMethodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(afterMethodAndAnnotation);
                methods.add(methodWrapper);
            }
            SimpleAdviseSupport simpleAdviseSupport = new SimpleAdviseSupport(methods);
            simpleAdviseSupports.add(simpleAdviseSupport);
        }
    }

    private <T extends Annotation> SimpleAdviseSupport.MethodWrapper getMethodWrapper(Map<Method, T> methodAndAnnotation) {
        Iterator<Method> iterator = methodAndAnnotation.keySet().iterator();
        Method method = null;
        while (iterator.hasNext()) {
            method = iterator.next();
        }
        assert method != null;
        T annotation = methodAndAnnotation.get(method);
        String annotationMeta = null;
        String annotationName = null;
        if (annotation instanceof SimplePointCut) {
            SimplePointCut spc = (SimplePointCut) annotation;
            annotationMeta = spc.express();
            annotationName = "SimplePointCut";
        } else if (annotation instanceof SimpleBefore) {
            SimpleBefore spc = (SimpleBefore) annotation;
            annotationMeta = spc.value();
            annotationName = "SimpleBefore";
        } else if (annotation instanceof SimpleAfter) {
            SimpleAfter spc = (SimpleAfter) annotation;
            annotationMeta = spc.value();
            annotationName = "SimpleAfter";

        }

        return new SimpleAdviseSupport.MethodWrapper(method.getName()
                , annotationName
                , annotationMeta, method);
    }

    protected void invokerBeanFactoryProcessor() throws Throwable {
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : this.getBeanDefinitions().entrySet()) {
            if (SimpleBeanFactoryPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanFactoryPostProcessor simpleBeanDefinitionRegistry = getBean(entry.getKey());
                simpleBeanDefinitionRegistry.postProcessBeanFactory(this);
            }
        }

    }

    protected void invokerBeanPostProcessor() throws Throwable {
        SimpleAutowiredAnnotationBeanPostProcessor simpleAutowiredAnnotationBeanPostProcessor = new SimpleAutowiredAnnotationBeanPostProcessor();
        simpleAutowiredAnnotationBeanPostProcessor.setBeanFactory(this);
        List<SimpleBeanPostProcessor> systemPostProcessors = new ArrayList<>();
        List<SimpleBeanPostProcessor> sortedPostProcessors = new ArrayList<>();
        systemPostProcessors.add(simpleAutowiredAnnotationBeanPostProcessor);
        List<SimpleBeanPostProcessor> nonOrderPostprocessors = new ArrayList<>();
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : this.getBeanDefinitions().entrySet()) {

            if (SimpleBeanPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanPostProcessor simplePostProcessor = getBean(entry.getKey());
                nonOrderPostprocessors.add(simplePostProcessor);
            }

        }
        sortPostProcessors(nonOrderPostprocessors, sortedPostProcessors);
        simplePostProcessors.addAll(systemPostProcessors);
        simplePostProcessors.addAll(sortedPostProcessors);

    }

    private void sortPostProcessors(List<SimpleBeanPostProcessor> nonOrderPostprocessors, List<SimpleBeanPostProcessor> sortedPostProcessors) {
        Map<Integer, SimpleBeanPostProcessor> markPostProcessorMap = new LinkedHashMap<>();
        for (SimpleBeanPostProcessor nonOrderPostprocessor : nonOrderPostprocessors) {
            if (ClassUtils.matchOrdered(nonOrderPostprocessor)) {
                markPostProcessorMap.put(getOrdered(nonOrderPostprocessor), nonOrderPostprocessor);
            } else {
                markPostProcessorMap.put(SimpleIOCConstant.DEFAULT_ORDER_VALUE, nonOrderPostprocessor);
            }
        }
        List<SimpleBeanPostProcessor> sortedPostProcessorsList = markPostProcessorMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        sortedPostProcessors.addAll(sortedPostProcessorsList);
    }

    private Integer getOrdered(SimpleBeanPostProcessor nonOrderPostprocessor) {
        return ClassUtils.getOrderedValue(nonOrderPostprocessor);
    }

    private SimpleRootBeanDefinition buildRootBeanDefinition(Class<?> clazz) {
        return SimpleRootBeanDefinition.builder().rootClass(clazz)
                .beanName(ClassUtils.toLowerBeanName(clazz.getSimpleName()))
                .isSingleton(true).build();
    }

    protected List<SimpleBeanPostProcessor> getBeanPostProcessor() {
        return this.simplePostProcessors;
    }

    public Map<String, SimpleRootBeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.beanDefinitions.put(beanName, (SimpleRootBeanDefinition) singletonObject);
    }


}

package com.github.simple.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.simple.core.annotation.*;
import com.github.simple.core.aop.SimpleAdviseSupport;
import com.github.simple.core.aop.SimpleAutoProxyCreator;
import com.github.simple.core.config.SimpleConfigBean;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.enums.SimpleIOCEnum;
import com.github.simple.core.exception.SimpleBeanCreateException;
import com.github.simple.core.exception.SimpleBeanDefinitionNotFoundException;
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
        if (ObjectUtil.isNull(sbf)) {
            throw new SimpleBeanDefinitionNotFoundException("the BeanDefinition not found:" + beanName);
        }
        Object bean;
        try {
            bean = getSingletonBean(beanName, () -> {
                        try {
                            return createBean(beanName, sbf);
                        } catch (Exception e) {
                            singletonObjectMap.clear();
                            throw new SimpleBeanCreateException(SimpleIOCEnum.CREATE_BEAN_ERROR.getMsg() + "[" + beanName + "]");
                        }
                    }
            );
        } catch (Exception e) {
            log.error("createBean exception!",e);
            throw new SimpleBeanCreateException("[" + beanName + "] getSingletonBean exception! errorMsg: [" + e.getMessage() + "]");
        }
        beanDefinitions.remove(beanName);
        return (T) bean;
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
    protected Object earlyRefSingleton(String beanName, Object wrapperInstance) {
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
        classSet.forEach(mbd -> {
            try {
                configBeanRegistry(mbd);
                generalRegistry(mbd);
                parseAspect(mbd, simpleAdviseSupports);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        registrySystemPostProcessor();
        spc.setSimpleAdviseSupports(simpleAdviseSupports);
        simplePostProcessors.add(spc);
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 配置bean注册 {@link SimpleConfig}
     * @date 5:20 下午 2020/12/17
     **/
    private void configBeanRegistry(Class<?> bdClazz) throws Throwable {
        if (ReflectUtils.matchAnnotationComponent(bdClazz, SimpleConfig.class)) {
            SimpleRootBeanDefinition configBeanDefinition = buildRootBeanDefinition(bdClazz);
            addBeanDefinition(configBeanDefinition.getBeanName(), configBeanDefinition);
            doParseAndRegistryConfigBean(bdClazz);
        }
    }

    private void doParseAndRegistryConfigBean(Class<?> bdClazz) throws Throwable {
        Map<Method, SimpleBean> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(bdClazz, SimpleBean.class);
        SimpleRootBeanDefinition configMbd = buildRootBeanDefinition(SimpleConfigBean.class);
        if (!beanDefinitions.containsKey(configMbd.getBeanName())) {
            addBeanDefinition(configMbd.getBeanName(), configMbd);
        }
        for (Map.Entry<Method, SimpleBean> entry : methodAndAnnotation.entrySet()) {
            SimpleRootBeanDefinition configBeanDefinition;
            if (!"".equals(entry.getValue().name())) {
                configBeanDefinition = buildRootBeanDefinition(entry.getKey().getReturnType(), entry.getValue().name(), true);
            } else {
                configBeanDefinition = buildRootBeanDefinition(entry.getKey().getReturnType());
            }
            addBeanDefinition(configBeanDefinition.getBeanName(), configBeanDefinition);
            SimpleConfigBean simpleBeanMethod = getBean(SimpleConfigBean.class);
            if (simpleBeanMethod != null) {
                if (simpleBeanMethod.getBeanMethods() == null) {
                    SimpleBeanMethod method = new SimpleBeanMethod(createMethodMeta(entry.getKey()), bdClazz);
                    simpleBeanMethod.setBeanMethods(method);
                } else {
                    simpleBeanMethod.getBeanMethods().getMethodMetadata().add(new SimpleMethodMeta(entry.getKey().getName(), entry.getKey()));
                }
            }
        }
    }

    private List<SimpleMethodMetadata> createMethodMeta(Method key) {
        List<SimpleMethodMetadata> methodMetadata = new ArrayList<>();
        SimpleMethodMetadata metadata = new SimpleMethodMeta(key.getName(), key);
        methodMetadata.add(metadata);
        return methodMetadata;
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 普通bean注册
     * @date 4:27 下午 2020/12/17
     **/
    private void generalRegistry(Class<?> cb) {
        if (ReflectUtils.matchAnnotationComponent(cb, SimpleComponent.class) || ReflectUtils.matchAnnotationComponent(cb, SimpleService.class)) {
            SimpleRootBeanDefinition rootBeanDefinition = buildRootBeanDefinition(cb);
            addBeanDefinition(rootBeanDefinition.getBeanName(), rootBeanDefinition);
        }
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 注册系统后置处理器
     * @date 4:28 下午 2020/12/17
     **/
    private void registrySystemPostProcessor() {
        SimpleRootBeanDefinition autowiredBeanDefinition = buildRootBeanDefinition(SimpleAutowiredAnnotationBeanPostProcessor.class);
        addBeanDefinition(autowiredBeanDefinition.getBeanName(), autowiredBeanDefinition);

    }

    /**
     * 解析aop切面
     *
     * @param clazz
     * @param simpleAdviseSupports
     */
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

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 获取切面方法包装
     * @date 4:28 下午 2020/12/17
     **/
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

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 调用工厂后置处理器
     * @date 4:29 下午 2020/12/17
     **/
    protected void invokerBeanFactoryProcessor() throws Throwable {
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : this.getBeanDefinitions().entrySet()) {
            if (SimpleBeanFactoryPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanFactoryPostProcessor simpleBeanDefinitionRegistry = getBean(entry.getKey());
                simpleBeanDefinitionRegistry.postProcessBeanFactory(this);
            }
        }

    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 调用bean后置处理器
     * @date 4:29 下午 2020/12/17
     **/
    protected void invokerBeanPostProcessor() throws Throwable {
        List<SimpleBeanPostProcessor> sortedPostProcessors = new ArrayList<>();
        List<SimpleBeanPostProcessor> nonOrderPostprocessors = new ArrayList<>();
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : getBeanDefinitions().entrySet()) {
            if (SimpleBeanPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanPostProcessor simplePostProcessor = getBean(entry.getKey());
                nonOrderPostprocessors.add(simplePostProcessor);
            }

        }
        sortPostProcessors(nonOrderPostprocessors, sortedPostProcessors);
        processInjectionBasedOnCurrentContext(sortedPostProcessors);
        simplePostProcessors.addAll(sortedPostProcessors);

    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 处理 {@link SimpleAutowiredAnnotationBeanPostProcessor}
     * @date 4:29 下午 2020/12/17
     **/
    protected abstract void processInjectionBasedOnCurrentContext(List<SimpleBeanPostProcessor> sortedPostProcessors);

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
        return buildRootBeanDefinition(clazz, ClassUtils.toLowerBeanName(clazz.getSimpleName()), true);
    }

    private SimpleRootBeanDefinition buildRootBeanDefinition(Class<?> clazz, String beanName, Boolean isSingleton) {
        return SimpleRootBeanDefinition.builder().rootClass(clazz)
                .beanName(beanName)
                .isSingleton(isSingleton).build();
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

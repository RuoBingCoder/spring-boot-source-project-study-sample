package com.github.simple.ioc.factory;

import com.github.simple.ioc.annotation.SimpleAutowiredAnnotationBeanPostProcessor;
import com.github.simple.ioc.annotation.SimpleBeanFactoryProcessor;
import com.github.simple.ioc.annotation.SimpleBeanPostProcessor;
import com.github.simple.ioc.constant.SimpleIOCConstant;
import com.github.simple.ioc.definition.SimpleRootBeanDefinition;
import com.github.simple.ioc.enums.SimpleIOCEnum;
import com.github.simple.ioc.exception.SimpleBeanCreateException;
import com.github.simple.ioc.exception.SimpleClassNotFoundException;
import com.github.simple.ioc.utils.ClassUtils;
import com.github.simple.ioc.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
    protected final Map<String, SimpleRootBeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    /**
     * bean后置处理器
     */
    protected List<SimpleBeanPostProcessor> simplePostProcessors=new ArrayList<>(128);

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
                        throw new SimpleBeanCreateException(SimpleIOCEnum.CREATE_BEAN_ERROR.getMsg());
                    }
                }
        );
    }

    /**
     *
     * @description: 简单获取bean定义
     * @param beanName
     * @return beanDefinition
     */
    private SimpleRootBeanDefinition getMergeBeanDefinition(String beanName) {
        return beanDefinitions.get(beanName);
    }

    protected abstract Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable;

    /**
     * @description: 转换beanName
     * @param name
     * @return beanName
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
    public  <T> T getBean(String name) throws Throwable {
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
     * @param wrapperInstance
     * @return obj 代理对象&本对象
     */
    protected Object earlySingleton(Object wrapperInstance) {
        //spring 此处为依赖注入检查bean是否为代理类,如果是则替换为代理类实现依赖注入
        return wrapperInstance;
    }

    public void doRegistryBeanDefinition(Set<Class<?>> classSet) {
        classSet.forEach(cb -> {
            if (ReflectUtils.matchAnnotationComponent(cb)) {
                SimpleRootBeanDefinition rootBeanDefinition = buildRootBeanDefinition(cb);
                addBeanDefinition(rootBeanDefinition.getBeanName(), rootBeanDefinition);
            }
        });
    }

    protected void invokerBeanFactoryProcessor() throws Throwable {
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : this.getBeanDefinitions().entrySet()) {
            if(SimpleBeanFactoryProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))){
                SimpleBeanFactoryProcessor simpleBeanDefinitionRegistry = getBean(entry.getKey());
                simpleBeanDefinitionRegistry.postProcessBeanFactory(this);
            }
        }

    }

     protected void invokerBeanPostProcessor() throws Throwable {
         SimpleAutowiredAnnotationBeanPostProcessor simpleAutowiredAnnotationBeanPostProcessor = new SimpleAutowiredAnnotationBeanPostProcessor();
         simpleAutowiredAnnotationBeanPostProcessor.setBeanFactory(this);
         List<SimpleBeanPostProcessor> systemPostprocessors=new ArrayList<>();
         List<SimpleBeanPostProcessor> sortedPostProcessors=new ArrayList<>();
         systemPostprocessors.add(simpleAutowiredAnnotationBeanPostProcessor);
         List<SimpleBeanPostProcessor> nonOrderPostprocessors=new ArrayList<>();
         for (Map.Entry<String, SimpleRootBeanDefinition> entry : this.getBeanDefinitions().entrySet()) {
             if(SimpleBeanPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))){
                 SimpleBeanPostProcessor simplePostProcessor = getBean(entry.getKey());
                 nonOrderPostprocessors.add(simplePostProcessor);
             }
         }
         sortPostProcessors(nonOrderPostprocessors,sortedPostProcessors);
         simplePostProcessors.addAll(systemPostprocessors);
         simplePostProcessors.addAll(nonOrderPostprocessors);

      }

    private void sortPostProcessors(List<SimpleBeanPostProcessor> nonOrderPostprocessors, List<SimpleBeanPostProcessor> sortedPostProcessors) {
        Map<Integer, SimpleBeanPostProcessor> markPostProcessorMap=new LinkedHashMap<>();
        for (SimpleBeanPostProcessor nonOrderPostprocessor : nonOrderPostprocessors) {
            if (ClassUtils.matchOrdered(nonOrderPostprocessor)) {
                markPostProcessorMap.put(getOrdered(nonOrderPostprocessor),nonOrderPostprocessor);
            }else {
                markPostProcessorMap.put(SimpleIOCConstant.DEFAULT_ORDER_VALUE,nonOrderPostprocessor);
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

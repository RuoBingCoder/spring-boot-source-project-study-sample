package com.github.simple.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.annotation.SimpleInstantiationAwareBeanPostProcessor;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.init.SimpleInitializingBean;
import com.github.simple.core.resource.SimpleClassPathResource;
import com.github.simple.core.resource.SimplePropertiesPropertySourceLoader;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/12/11 6:32 下午
 * @description: 能力bean工厂
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 */
@Slf4j
public abstract class SimpleAutowiredCapableBeanFactory extends AbsBeanFactory {

    protected   List<SimplePropertySource<Properties>> simplePropertiesPropertySourceLoader;

    protected SimpleAutowiredCapableBeanFactory(String basePackages) throws Throwable {
        try {
            registryBeanDef(basePackages);
            prepareEnvSource();
            invokerBeanFactoryProcessor();
            invokerBeanPostProcessor();
            finishBeanInstance();
        } catch (Exception e) {
            destroyBeans();
        }
    }

    private void prepareEnvSource() {
        SimpleClassPathResource  source=new SimpleClassPathResource(SimpleIOCConstant.DEFAULT_SOURCE_NAME);
        SimplePropertiesPropertySourceLoader loader=new SimplePropertiesPropertySourceLoader();
        simplePropertiesPropertySourceLoader= loader.load(source.getFilename(), source);

    }


    private void destroyBeans() {
        super.simplePostProcessors.clear();
        super.earlySingletonMap.clear();
    }


    public void finishBeanInstance() throws Throwable {
        Map<String, SimpleRootBeanDefinition> beanDefinitionMap = this.getBeanDefinitions();
        List<String> beanNames = new ArrayList<>(beanDefinitionMap.keySet());
        for (String beanName : beanNames) {
            this.getBean(beanName);
        }

    }


    public void registryBeanDef(String basePackages) {
        Set<Class<?>> classSet = ClassUtils.scannerBasePackages(basePackages);
        doRegistryBeanDefinition(classSet);
    }

    @Override
    protected Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable {
        return doCreateBean(beanName);
    }

    private Object doCreateBean(String beanName) throws Throwable {
        //创建bean
        //填充属性
        //初始化
        SimpleRootBeanDefinition simpleRootBeanDefinition = beanDefinitions.get(beanName);
        Object instance = null;
        if (simpleRootBeanDefinition.getIsSingleton()) {
            instance = createInstance(simpleRootBeanDefinition.getRootClass());
        }
        //添加aop代理bean
        Object exportObject = instance;
        addSingletonFactory(beanName, () ->earlyRefSingleton(beanName, exportObject));
        //填充属性
        populateBean(beanName, exportObject);
        //初始化bean
        return initialization(beanName, exportObject);
    }

    private Object initialization(String beanName, Object instance) {
        invokerAware(beanName, instance);
        invokerBeforeInitialization(beanName, instance);
        try {
            initMethods(beanName, instance);
        } catch (Exception e) {
            log.error("invoker initMethods error!", e);
            throw new SimpleIOCBaseException("invoker initMethods error");
        }
        Object shareObject = invokerAfterInitialization(beanName, instance);
        if (shareObject != null){
            return shareObject;
        }
        return instance;


    }

    private Object invokerAfterInitialization(String beanName, Object instance) {
        if (CollectionUtil.isEmpty(getBeanPostProcessor())) {
            return instance;
        }
        for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
            Object bean = simplePostProcessor.postProcessAfterInitialization(instance, beanName);
            if (bean != null){
                return bean;
            }
        }
        return instance;
    }

    private void invokerBeforeInitialization(String beanName, Object instance) {
        if (CollectionUtil.isEmpty(getBeanPostProcessor())) {
            return;
        }
        for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
            Object bean = simplePostProcessor.postProcessBeforeInitialization(instance, beanName);
            if (ObjectUtil.isNotNull(bean)) {
                return;
            }
        }
    }

    private void initMethods(String beanName, Object instance) {
        if (instance instanceof SimpleInitializingBean) {
            SimpleInitializingBean simpleInitializingBean = (SimpleInitializingBean) instance;
            simpleInitializingBean.afterPropertiesSet();

        }
    }

    private void invokerAware(Object instance, Object o) {
        if (instance instanceof SimpleBeanFactoryAware) {
            SimpleBeanFactoryAware simpleBeanFactoryAware = (SimpleBeanFactoryAware) instance;
            simpleBeanFactoryAware.setBeanFactory(this);

        }
    }

    /**
     * 属性填充
     *
     * @param beanName
     * @param instance
     * @throws Throwable
     */
    private void populateBean(String beanName, Object instance) throws Throwable {
        if (CollectionUtil.isNotEmpty(getBeanPostProcessor())) {
            for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
                if (simplePostProcessor instanceof SimpleInstantiationAwareBeanPostProcessor) {
                    SimpleInstantiationAwareBeanPostProcessor sbp = (SimpleInstantiationAwareBeanPostProcessor) simplePostProcessor;
                    if (!sbp.postProcessAfterInstantiation(instance, beanName)) {
                        return;
                    }
                    sbp.postProcessProperties(instance, beanName);
                }
            }

        }
    }


    private Object createInstance(Class<?> beanClass) throws InstantiationException, IllegalAccessException {
        return ClassUtils.newInstance(beanClass);

    }

    public void addSingletonFactory(String beanName, SimpleObjectFactory<?> factory) {
        synchronized (singletonFactoryMap) {
            singletonFactoryMap.put(beanName, factory);
        }

    }


    public Map<String, Object> getBeans() {
        return singletonObjectMap;
    }

    @Override
    public  List<SimplePropertySource<Properties>> getResource() {
        if (simplePropertiesPropertySourceLoader==null){
            throw new SimpleIOCBaseException("resource is null!");
        }
        return simplePropertiesPropertySourceLoader;
    }
}

package com.github.simple.ioc.factory;

import com.github.simple.ioc.annotation.SimpleAutowiredAnnotationBeanPostProcessor;
import com.github.simple.ioc.annotation.SimplePostProcessor;
import com.github.simple.ioc.constant.SimpleIOCConstant;
import com.github.simple.ioc.definition.SimpleRootBeanDefinition;
import com.github.simple.ioc.utils.ClassUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/12/11 6:32 下午
 * @description: SimpleAutowiredCapableBeanFactory
 */

public class SimpleAutowiredCapableBeanFactory extends AbsBeanFactory {
    public SimpleAutowiredCapableBeanFactory() {
        initBeanDefinitionBean();
        invokerBeanPostProcessor();
    }

    private void invokerBeanPostProcessor() {
        SimpleAutowiredAnnotationBeanPostProcessor beanPostProcessor = new SimpleAutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(this);
        super.simplePostProcessors = Collections.singletonList(beanPostProcessor);

    }

    public void initBeanDefinitionBean() {
        Set<Class<?>> classSet = ClassUtils.scannerBasePackages(SimpleIOCConstant.BASE_PACKAGE_NAME);
        doSetBeanDefinition(classSet);
    }

    @Override
    protected Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable {
        return doCreateBean(beanName);
    }

    private Object doCreateBean(String beanName) throws Throwable {
        //创建bean
        //填充属性
        //初始化
        SimpleRootBeanDefinition simpleRootBeanDefinition = beanDefinitionMap.get(beanName);
        Object instance = null;
        if (simpleRootBeanDefinition.getIsSingleton()) {
            instance = createInstance(simpleRootBeanDefinition.getRootClass());
        }
        Object finalInstance = instance;
        addSingletonFactory(beanName, () -> earlySingleton(finalInstance));
        populateBean(beanName, instance);
        initialization(beanName, instance);
        return finalInstance;
    }

    private void initialization(String beanName, Object instance) {
        //TODO
    }

    /**
     * 属性填充
     *
     * @param beanName
     * @param instance
     * @throws Throwable
     */
    private void populateBean(String beanName, Object instance) throws Throwable {
        for (SimplePostProcessor simplePostProcessor : getBeanPostProcessor()) {
            if (!simplePostProcessor.postProcessAfterInstantiation(instance, beanName)) {
                return;
            }
        }
        for (SimplePostProcessor spc : getBeanPostProcessor()) {
            spc.postProcessProperties(instance, beanName);
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
}

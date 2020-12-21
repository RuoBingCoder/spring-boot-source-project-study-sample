package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.factory.SimpleConfigBeanFactory;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 6:02 下午
 * @description: 自定义注册bean
 */
@SimpleComponent
public class CustomBeanFactoryProcessor implements SimpleBeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(SimpleConfigBeanFactory factory) {
        SimpleRootBeanDefinition subTwo = SimpleRootBeanDefinition.builder().beanName("beanFactoryRegistryBeanTest").isSingleton(true).rootClass(BeanFactoryRegistryBeanTest.class).build();
        factory.registerSingleton("beanFactoryRegistryBeanTest", subTwo);
        factory.registerResolvableDependency(String.class, "hello word!");
    }
}

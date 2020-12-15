package com.github.simple.demo.service;

import com.github.simple.ioc.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.ioc.annotation.SimpleComponent;
import com.github.simple.ioc.definition.SimpleRootBeanDefinition;
import com.github.simple.ioc.factory.SimpleBeanFactory;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 6:02 下午
 * @description: CustomBeanFactoryPorcessor
 */
@SimpleComponent
public class CustomBeanFactoryProcessor implements SimpleBeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(SimpleBeanFactory factory) {
        SimpleRootBeanDefinition subTwo = SimpleRootBeanDefinition.builder().beanName("subTwo").isSingleton(true).rootClass(SubTwo.class).build();
        factory.registerSingleton("subTwo",subTwo);
    }
}

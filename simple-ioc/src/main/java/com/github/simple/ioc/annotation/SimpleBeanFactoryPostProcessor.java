package com.github.simple.ioc.annotation;

import com.github.simple.ioc.factory.SimpleBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/14 5:41 下午
 * @description: SimpleBeanDefinitionRegistry
 */
public interface SimpleBeanFactoryPostProcessor {

    void postProcessBeanFactory(SimpleBeanFactory factory);
}

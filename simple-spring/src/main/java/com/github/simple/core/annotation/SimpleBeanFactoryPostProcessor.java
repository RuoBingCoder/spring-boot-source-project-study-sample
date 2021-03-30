package com.github.simple.core.annotation;

import com.github.simple.core.beans.factory.SimpleConfigBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/14 5:41 下午
 * @description: SimpleBeanDefinitionRegistry
 */
public interface SimpleBeanFactoryPostProcessor {

    void postProcessBeanFactory(SimpleConfigBeanFactory factory);
}

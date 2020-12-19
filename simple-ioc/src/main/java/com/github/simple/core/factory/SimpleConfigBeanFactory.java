package com.github.simple.core.factory;

import com.github.simple.core.annotation.SimpleBeanPostProcessor;

/**
 * @author: JianLei
 * @date: 2020/12/19 8:29 下午
 * @description: SimpleConfigBeanFactory
 */
public interface SimpleConfigBeanFactory extends SimpleBeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor);


    void setClassLoader(ClassLoader classLoader);



}

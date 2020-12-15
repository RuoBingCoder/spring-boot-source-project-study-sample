package com.github.simple.ioc.factory;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:26 下午
 * @description: BeanFactory
 */
public interface SimpleBeanFactory {

    <T> T getBean(Class<?> clazz) throws Throwable;

    <T> T getBean(String name) throws Throwable;

    default void registerSingleton(String beanName, Object singletonObject){

    }


}

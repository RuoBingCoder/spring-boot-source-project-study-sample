package com.github.simple.core.beans.factory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:26 下午
 * @description: BeanFactory
 */
public interface SimpleBeanFactory{

    <T> T getBean(Class<?> clazz) throws Throwable;

    <T> T getBean(String name) throws Throwable;

    default void registerBeanDefinition(String beanName, Object singletonObject){

    }

    default <T> T getResource(){
        return null;
    }


    default <T> List<T> getBeanForType(Class<?> clazz,Class<T> type) throws Throwable {
        return null;
    }

    Object resolveDependency(Field type, String beanName) throws Throwable;




}

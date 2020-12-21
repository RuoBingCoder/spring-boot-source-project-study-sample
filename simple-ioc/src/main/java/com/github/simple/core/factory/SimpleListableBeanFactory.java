package com.github.simple.core.factory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/14 3:41 下午
 * @description: ListableBeanFactory
 */
public interface SimpleListableBeanFactory extends SimpleBeanFactory, SimpleSingletonBeanRegistry {

    <T> Map<String, T> getBeansOfType(Class<T> clazz) throws Throwable;

    String[] getBeanNames();


    @Override
    default <T> T getBean(Class<?> clazz) throws Throwable {
        return null;
    }

    @Override
    default <T> T getBean(String name) throws Throwable {
        return null;
    }

    @Override
    default void registerBeanDefinition(String beanName, Object singletonObject) {

    }


    @Override
    default Object resolveDependency(Field type, String beanName) throws Throwable {
        return null;
    }
}

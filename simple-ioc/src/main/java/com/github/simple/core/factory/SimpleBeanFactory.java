package com.github.simple.core.factory;

import com.github.simple.core.resource.SimplePropertySource;

import java.util.List;
import java.util.Properties;

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

    default List<SimplePropertySource<Properties>> getResource(){
        return null;
    }


    default <T> List<T> getBeanForType(Class<?> clazz,Class<?> type) throws Throwable {
        return null;
    }

}

package com.github.simple.core.beans;

/**
 * @author: jianlei.shi
 * @date: 2020/12/19 3:08 下午
 * @description: SimpleFactoryBean
 */

public interface SimpleFactoryBean<T> {

    T getObject();

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}

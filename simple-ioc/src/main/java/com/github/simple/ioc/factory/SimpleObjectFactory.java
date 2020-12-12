package com.github.simple.ioc.factory;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:31 下午
 * @description: SingletonFacatory
 */
@FunctionalInterface
public interface SimpleObjectFactory<T> {

    T getObject() throws Throwable;
}

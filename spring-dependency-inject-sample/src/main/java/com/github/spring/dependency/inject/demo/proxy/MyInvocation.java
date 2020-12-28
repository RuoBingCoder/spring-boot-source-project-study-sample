package com.github.spring.dependency.inject.demo.proxy;

/**
 * @author: JianLei
 * @date: 2020/9/28 3:26 下午
 * @description: MyInvocation
 */

public interface MyInvocation<T> {

     boolean invoke(T t) throws IllegalAccessException, InstantiationException;
}

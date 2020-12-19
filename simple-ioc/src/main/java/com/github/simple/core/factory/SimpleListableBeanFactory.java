package com.github.simple.core.factory;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/14 3:41 下午
 * @description: ListableBeanFactory
 */
public interface SimpleListableBeanFactory extends SimpleBeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> clazz) throws Throwable;

    String[]  getBeanNames();



}

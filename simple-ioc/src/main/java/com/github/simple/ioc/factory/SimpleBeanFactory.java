package com.github.simple.ioc.factory;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:26 下午
 * @description: BeanFactory
 */
public interface SimpleBeanFactory {

    Object getBean(Class<?> clazz) throws Throwable;

    Object getBean(String name) throws Throwable;

    Map<String, Object> getBeanOfType(Class<?> clazz);
}

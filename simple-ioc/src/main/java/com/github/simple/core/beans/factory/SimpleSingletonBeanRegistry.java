package com.github.simple.core.beans.factory;

/**
 * @author: JianLei
 * @date: 2020/12/21 2:50 下午
 * @description: SimpleSingleonBeanRegistry
 */
public interface SimpleSingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

}

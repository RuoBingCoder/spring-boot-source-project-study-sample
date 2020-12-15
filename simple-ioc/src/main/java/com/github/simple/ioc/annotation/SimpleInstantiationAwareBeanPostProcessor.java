package com.github.simple.ioc.annotation;

/**
 * @author: JianLei
 * @date: 2020/12/15 1:59 下午
 * @description: SimpleInstantiationAwareBeanPostProcessor
 */
public interface SimpleInstantiationAwareBeanPostProcessor extends SimpleBeanPostProcessor{


    default Boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    default Object postProcessBeforeInstantiation(Class<?> clazz, String beanName) {
        return null;
    }
    default void postProcessProperties(Object bean, String beanName) throws Throwable {
    }
}

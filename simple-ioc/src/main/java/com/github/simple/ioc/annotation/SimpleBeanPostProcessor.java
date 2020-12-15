package com.github.simple.ioc.annotation;

/**
 * @author: JianLei
 * @date: 2020/12/12 1:41 下午
 * @description: SimplePostProcessor
 */
public interface SimpleBeanPostProcessor {

    default Boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    default Object postProcessBeforeInstantiation(Class<?> clazz, String beanName) {
        return null;
    }

    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }
    default void postProcessProperties(Object bean, String beanName) throws Throwable {
    }
}

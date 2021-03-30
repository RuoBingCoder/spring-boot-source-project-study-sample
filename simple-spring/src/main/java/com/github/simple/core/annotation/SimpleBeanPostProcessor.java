package com.github.simple.core.annotation;

/**
 * @author: JianLei
 * @date: 2020/12/12 1:41 下午
 * @description: SimplePostProcessor
 */
public interface SimpleBeanPostProcessor {



    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        return null;
    }

}

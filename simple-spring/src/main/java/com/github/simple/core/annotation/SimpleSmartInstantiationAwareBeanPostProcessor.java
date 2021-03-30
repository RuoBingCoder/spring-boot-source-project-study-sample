package com.github.simple.core.annotation;

import org.springframework.beans.BeansException;

/**
 * @author: JianLei
 * @date: 2020/12/15 8:06 下午
 * @description: SimpleSmartInstantiationAwareBeanPostProcessor
 */
public interface SimpleSmartInstantiationAwareBeanPostProcessor extends SimpleInstantiationAwareBeanPostProcessor {

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {

        return bean;
    }
}

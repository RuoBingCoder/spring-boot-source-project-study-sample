package com.github.simple.core.factory;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:31 下午
 * @description: SingletonFacatory
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#addCandidateEntry
 * @see org.springframework.beans.factory.config.DependencyDescriptor#resolveCandidate(String, Class, BeanFactory)
 */
@FunctionalInterface
public interface SimpleObjectFactory<T> {

    T getObject() throws Throwable;
}

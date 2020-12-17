package com.github.simple.core.config;

import com.github.simple.core.annotation.SimpleBeanMethod;
import com.github.simple.core.annotation.SimpleMethodMetadata;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 2:47 下午
 * @description: SimpleConfigBeanMetadata
 */

public abstract class SimpleConfigBeanMetadata {

    protected SimpleBeanMethod beanMethods;


    public SimpleBeanMethod getBeanMethods() {
        return beanMethods;
    }

    public void setBeanMethods(SimpleBeanMethod beanMethods) {
        this.beanMethods = beanMethods;
    }

    public boolean matchMethodName(String name) {
        return beanMethods.getMethodMetadata().stream().anyMatch(s -> s.getMethodName().equals(name));
    }

    public Method getConfigBeanMethod(String name) {
        return beanMethods.getMethodMetadata().stream().filter(s -> s.getMethodName().equals(name)).map(SimpleMethodMetadata::getMethod).collect(Collectors.toList()).get(0);
    }
}

package com.github.spring.dependency.inject.demo.bean;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/27 7:57 下午
 * @description: MyAsyncHolder
 */

public class MyAsyncHolder {

    private Class<?> bean;
    private List<Method> methods;

    public MyAsyncHolder(Class<?> bean, List<Method> methods) {
        this.bean = bean;
        this.methods = methods;
    }

    public MyAsyncHolder() {
    }

    public Class<?> getBean() {
        return bean;
    }

    public void setBean(Class<?> bean) {
        this.bean = bean;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}

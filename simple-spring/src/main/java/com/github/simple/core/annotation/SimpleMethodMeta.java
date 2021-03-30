package com.github.simple.core.annotation;

import java.lang.reflect.Method;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:39 下午
 * @description: SimpleMethodMeta
 */

public class SimpleMethodMeta implements SimpleMethodMetadata {
    private String methodName;
    private Method method;

    public SimpleMethodMeta(String methodName, Method method) {
        this.methodName = methodName;
        this.method = method;
    }

    public SimpleMethodMeta() {
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public String getDeclaringClassName() {
        return null;
    }

    @Override
    public String getReturnTypeName() {
        return null;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isOverridable() {
        return false;
    }

    @Override
    public Method getMethod() {
        return method;
    }
}

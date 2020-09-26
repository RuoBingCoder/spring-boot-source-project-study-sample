package com.sjl.custom.aop.definition;

import lombok.Data;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:22 下午
 * @description: 定义代理Bean属性
 */
public class AopBeanDefinition {

    private String pointcut;
    private Class<?> targetClass;
    private Object target;
    private String before;
    private String beforeMethodName;
    private String after;
    private String afterMethodName;
    private Class<?> aspectClass;
    private Object aspectTarget;


    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getBeforeMethodName() {
        return beforeMethodName;
    }

    public void setBeforeMethodName(String beforeMethodName) {
        this.beforeMethodName = beforeMethodName;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getAfterMethodName() {
        return afterMethodName;
    }

    public void setAfterMethodName(String afterMethodName) {
        this.afterMethodName = afterMethodName;
    }

    public Class<?> getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(Class<?> aspectClass) {
        this.aspectClass = aspectClass;
    }

    public Object getAspectTarget() {
        return aspectTarget;
    }

    public void setAspectTarget(Object aspectTarget) {
        this.aspectTarget = aspectTarget;
    }
}

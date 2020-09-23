package com.sjl.custom.aop.bean;

import lombok.Data;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:22 下午
 * @description: 定义代理Bean属性
 */
@Data
public class AopBeanDefinition {

    private String expression;
    private Class<?> targetClass;
    private Object target;
    private String before;
    private String beforeMethodName;
    private String after;
    private String afterMethodName;
    private Class<?> aspectClass;
    private Object aspectTarget;

}

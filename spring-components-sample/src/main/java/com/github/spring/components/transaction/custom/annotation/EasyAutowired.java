package com.github.spring.components.transaction.custom.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/11/8 2:58 下午
 * @description: EasyAutowired
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EasyAutowired {
}

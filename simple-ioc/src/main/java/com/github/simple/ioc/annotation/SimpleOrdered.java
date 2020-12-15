package com.github.simple.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/12/15 11:43 上午
 * @description: SimpleOrdered
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleOrdered {
    int value() default 1;
}

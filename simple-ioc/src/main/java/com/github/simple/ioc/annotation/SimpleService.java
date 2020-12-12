package com.github.simple.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/12/12 11:08 下午
 * @description: SimpleService
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleService {


    String value() default "";
}

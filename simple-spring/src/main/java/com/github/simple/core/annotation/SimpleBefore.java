package com.github.simple.core.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/12/11 9:45 下午
 * @description: SimpleAutowired
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleBefore {

    String value() default "";
}

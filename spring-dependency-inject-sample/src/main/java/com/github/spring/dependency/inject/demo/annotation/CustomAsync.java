package com.github.spring.dependency.inject.demo.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/9/27 7:53 下午
 * @description: MyAsync
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomAsync {
    String value() default "";
}

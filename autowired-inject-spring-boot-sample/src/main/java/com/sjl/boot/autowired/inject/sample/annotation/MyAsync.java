package com.sjl.boot.autowired.inject.sample.annotation;

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
public @interface MyAsync {
    String value() default "";
}
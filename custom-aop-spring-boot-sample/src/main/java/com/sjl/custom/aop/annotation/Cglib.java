package com.sjl.custom.aop.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/9/29 11:20 上午
 * @description: CglibProxy
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Cglib {
}

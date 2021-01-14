package com.github.tomcat.core.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:12 下午
 * @description: EasyController
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyRequestParams {
    String value() default "";
}

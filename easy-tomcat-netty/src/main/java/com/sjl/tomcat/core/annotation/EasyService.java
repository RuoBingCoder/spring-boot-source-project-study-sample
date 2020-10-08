package com.sjl.tomcat.core.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:12 下午
 * @description: EasyController
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyService {

    String name() default "";
}

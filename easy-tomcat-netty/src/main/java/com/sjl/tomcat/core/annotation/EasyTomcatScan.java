package com.sjl.tomcat.core.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/10/8 8:15 下午
 * @description: EasyTomcatScan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyTomcatScan {

    String basePackageName() default "";
}

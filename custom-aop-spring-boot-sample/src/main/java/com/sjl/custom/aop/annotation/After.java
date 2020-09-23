package com.sjl.custom.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface After {
    String value();

    String argNames() default "";
}

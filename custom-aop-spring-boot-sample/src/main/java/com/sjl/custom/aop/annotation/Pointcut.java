package com.sjl.custom.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pointcut {
    String value() default "";

    String argNames() default "";
}

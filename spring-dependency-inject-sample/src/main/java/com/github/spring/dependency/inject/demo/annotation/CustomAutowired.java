package com.github.spring.dependency.inject.demo.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomAutowired {
    String value() default "";
    String version() default "1.0.0";
    String group() default "";
}

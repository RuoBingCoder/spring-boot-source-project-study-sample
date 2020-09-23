package com.sjl.boot.autowired.inject.sample.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAutowired {
    String value() default "";
    String version() default "1.0.0";
    String group() default "";
}

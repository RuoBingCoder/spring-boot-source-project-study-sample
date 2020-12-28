package com.github.spring.dependency.inject.demo.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomValue {
    String  value()default "";
}

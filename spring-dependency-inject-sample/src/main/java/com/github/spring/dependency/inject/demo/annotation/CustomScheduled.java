package com.github.spring.dependency.inject.demo.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomScheduled {
    String cron() default "* * * * * *";
}

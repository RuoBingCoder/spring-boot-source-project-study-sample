package com.sjl.boot.autowired.inject.sample.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomScheduled {
    String cron() default "* * * * * *";
}

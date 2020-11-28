package com.sjl.boot.autowired.inject.sample.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomValue {
    String  value()default "";
}

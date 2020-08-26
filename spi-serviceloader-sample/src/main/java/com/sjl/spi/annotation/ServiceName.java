package com.sjl.spi.annotation;

import java.lang.annotation.*;

/**
 * @author: jianlei
 * @date: 2020/8/26
 * @description: ServiceName
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ServiceName {
    String value() default "";
}

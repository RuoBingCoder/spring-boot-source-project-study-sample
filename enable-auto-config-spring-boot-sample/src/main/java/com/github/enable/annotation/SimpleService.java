package com.github.enable.annotation;

import java.lang.annotation.*;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SjlService
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SimpleService {

}

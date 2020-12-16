package com.github.simple.core.annotation;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/12/11 9:43 下午
 * @description: SimpleComponent
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleComponent {

    String value() default "";
}

package com.sjl.boot.autowired.inject.sample.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/11/28 下午1:57
 * @description: EnableMyAsync
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CustomAsyncAnnotationDefinitionRegistry.class)
public @interface EnableCustomAsync {

    String value() default "Proxy";
}

package com.github.spring.components.learning.enhancemybatis.annoation;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2020/12/30 7:01 下午
 * @description: Table
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Column {

    String name();
}

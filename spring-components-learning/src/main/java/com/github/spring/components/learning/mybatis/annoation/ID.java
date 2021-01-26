package com.github.spring.components.learning.mybatis.annoation;


import com.github.spring.components.learning.mybatis.common.constant.SqlTagConstant;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2020/12/30 7:01 下午
 * @description: Table
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ID {

    String name() default SqlTagConstant.PRIMARY_KEY;
}

package com.github.simple.core.rpc.annotaion;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2021/2/14 8:46 下午
 * @description: RbValue
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RbValue {
    //todo
    String value();
}

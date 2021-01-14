package com.github.custom.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/25 5:44 下午
 * @description:
 */

public interface JoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

}

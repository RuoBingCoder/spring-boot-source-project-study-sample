package com.sjl.spring.components.transaction.custom.handle;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:49 上午
 * @description: MethodInvocation
 */
public interface Invocation {

    Object proceed() throws InvocationTargetException, IllegalAccessException;
}

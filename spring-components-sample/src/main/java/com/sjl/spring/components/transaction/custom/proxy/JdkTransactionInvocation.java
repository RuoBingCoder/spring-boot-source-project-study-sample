package com.sjl.spring.components.transaction.custom.proxy;

import com.sjl.spring.components.transaction.custom.handle.MethodInvocation;
import com.sjl.spring.components.transaction.custom.suuport.TransactionAdviserSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:14 上午
 * @description: TransactionHandler
 */

public class JdkTransactionInvocation implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(method, args);
        return methodInvocation.proceed();
    }
}

package com.sjl.custom.aop.interceptor;

import com.sjl.custom.aop.aspect.MethodInvocation;

public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}

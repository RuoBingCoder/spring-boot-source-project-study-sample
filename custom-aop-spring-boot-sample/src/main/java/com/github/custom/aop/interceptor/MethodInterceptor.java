package com.github.custom.aop.interceptor;

import com.github.custom.aop.aspect.MethodInvocation;

public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}

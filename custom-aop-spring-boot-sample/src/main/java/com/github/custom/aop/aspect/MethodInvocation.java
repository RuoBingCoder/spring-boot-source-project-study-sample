package com.github.custom.aop.aspect;

import com.github.custom.aop.interceptor.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/25 5:44 下午
 * @description:
 *
 */

public class MethodInvocation implements JoinPoint{
    private final Method method;
    private final Object target;
    private final Object [] arguments;
    private final List<Object> interceptorsAndDynamicMethodMatchers;

    public MethodInvocation(Method method, Object target, Object[] arguments, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.method = method;
        this.target = target;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }
//定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;
    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    public Object proceed() throws Throwable {
        //如果Interceptor执行完了，则执行joinPoint
        // interceptorsAndDynamicMethodMatchers 存放执行方法列表
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target,this.arguments);
        }

        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor) {
            MethodInterceptor mi =
                    (MethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            //动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
            return proceed();
        }
    }

}

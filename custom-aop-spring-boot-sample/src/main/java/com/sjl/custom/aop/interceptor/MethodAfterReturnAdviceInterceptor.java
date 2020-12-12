package com.sjl.custom.aop.interceptor;

import com.sjl.custom.aop.aspect.AbstractAspectAdvice;
import com.sjl.custom.aop.aspect.Advice;
import com.sjl.custom.aop.aspect.JoinPoint;
import com.sjl.custom.aop.aspect.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/26 12:34 下午
 * @description:
 */

public class MethodAfterReturnAdviceInterceptor extends AbstractAspectAdvice
    implements Advice, MethodInterceptor {
  private JoinPoint joinPoint;

  public MethodAfterReturnAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
    super(aspectMethod, aspectTarget);
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object result = invocation.proceed();
    joinPoint = invocation;
    afterReturning(result);
    return result;
  }

  public void afterReturning(Object returnValue) throws Throwable {
    super.invokeAdviceMethod(joinPoint,returnValue,null);
  }
}

package com.sjl.custom.aop.proxy;

import com.sjl.custom.aop.aspect.MethodInvocation;
import com.sjl.custom.aop.support.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:30 下午
 * @description:
 */
public class JdkInvocation implements InvocationHandler {

  private final AdvisedSupport advised;

  public JdkInvocation(AdvisedSupport advised) {
    this.advised = advised;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //调用before方法
    List<Object> advice = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, advised.getTargetClass());
    MethodInvocation invocation=new MethodInvocation(method,this.advised.getTarget(),args,advice);
   //执行调用链
    return invocation.proceed();
  }



}

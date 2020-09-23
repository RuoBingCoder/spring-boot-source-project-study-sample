package com.sjl.boot.autowired.inject.sample.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类
 *
 * @param <T>
 */
@Slf4j
public class RpcServiceProxy<T> implements InvocationHandler {

  private T target;

  public RpcServiceProxy(T target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String className = method.getDeclaringClass().getName();
    String methodName = method.getName();
    System.out.println("calssName is:"+className+"---"+"methodName:  "+methodName);
    return null;
  }
}

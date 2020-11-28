package com.sjl.boot.autowired.inject.sample.proxy;

import com.sjl.boot.autowired.inject.sample.bean.MyAsyncHolder;
import com.sjl.boot.autowired.inject.sample.utils.ThreadPoolUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:03 上午
 * @description: MyAsyncProxy
 */
public class JdkDynamicHandler implements InvocationHandler {
  private final AtomicInteger integer = new AtomicInteger();
  private MyAsyncHolder myAsyncHolder;

  public JdkDynamicHandler(MyAsyncHolder myAsyncHolder) {
    this.myAsyncHolder = myAsyncHolder;
  }

  public JdkDynamicHandler() {}

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("开始MyAsync代理........");
    Object proxyBean = myAsyncHolder.getBean().newInstance();
    AsyncInvocationTask asyncHolder=new AsyncInvocationTask(method,args);
    boolean invoke = asyncHolder.invoke(myAsyncHolder);
    if (!invoke){
      System.out.println("=============invoke===================");
      return method.invoke(proxyBean, args);
    }
    return null;

  }
}

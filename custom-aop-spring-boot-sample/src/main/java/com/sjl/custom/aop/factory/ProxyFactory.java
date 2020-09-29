package com.sjl.custom.aop.factory;

import com.sjl.custom.aop.interceptor.CglibMethodInterceptor;
import com.sjl.custom.aop.proxy.CustomProxy;
import com.sjl.custom.aop.proxy.JdkInvocation;
import com.sjl.custom.aop.support.AdvisedSupport;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:56 下午
 * @description:
 */
public class ProxyFactory implements CustomProxy {
  private final AdvisedSupport support;
  private static volatile ProxyFactory proxyFactory;
  private static final Enhancer en=new Enhancer();
  public ProxyFactory(AdvisedSupport support) {
    this.support = support;
  }

  public static ProxyFactory getProxyFactory(AdvisedSupport support) {
    if (proxyFactory == null) {
      synchronized (ProxyFactory.class) {
        if (proxyFactory == null) {
          return new ProxyFactory(support);
        }
      }
    }
    return null;
  }

  @Override
  public <T> T createJDKProxy(Class<?> clazz) {
    return (T)
        Proxy.newProxyInstance(
            support.getTarget().getClass().getClassLoader(),
            new Class[] {this.support.getTargetClass()},
            new JdkInvocation(support));
  }

  public static <T> T getJDKProxy(AdvisedSupport support) {
    return Objects.requireNonNull(getProxyFactory(support)).createJDKProxy(null);
  }

  @Override
  public <T> T createCGLIBProxy(Class<?> clazz) {
    en.setSuperclass(clazz);
    en.setCallback(new CglibMethodInterceptor());
    return (T) en.create();
  }

  public static  <T> T getCGLIBProxy(Class<?> clazz) {
   return Objects.requireNonNull(getProxyFactory(null)).createCGLIBProxy(clazz);
  }
}

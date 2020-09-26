package com.sjl.custom.aop.factory;

import com.sjl.custom.aop.proxy.JdkProxy;
import com.sjl.custom.aop.support.AdvisedSupport;

import java.lang.reflect.Proxy;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:56 下午
 * @description:
 */
public class ProxyFactory {
  private AdvisedSupport support;

  public ProxyFactory(AdvisedSupport support) {
    this.support = support;
  }

  public  <T> T crateProxy() {
    return (T)
        Proxy.newProxyInstance(
                support.getTarget().getClass().getClassLoader(), new Class[] {this.support.getTargetClass()}, new JdkProxy(support));
  }

}

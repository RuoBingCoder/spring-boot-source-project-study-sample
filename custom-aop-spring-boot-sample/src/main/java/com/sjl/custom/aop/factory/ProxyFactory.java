package com.sjl.custom.aop.factory;

import com.sjl.custom.aop.bean.AopBeanDefinition;
import com.sjl.custom.aop.bean.AspectHolder;
import com.sjl.custom.aop.proxy.JdkProxy;

import java.lang.reflect.Proxy;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:56 下午
 * @description:
 */
public class ProxyFactory {
  private  AspectHolder<AopBeanDefinition> holder;

  public ProxyFactory(AspectHolder<AopBeanDefinition> holder) {
    this.holder = holder;
  }
  public  <T> T crateProxy() {
    return (T)
        Proxy.newProxyInstance(
                holder.getConfigAttribute().get(0).getTarget().getClass().getClassLoader(), new Class[] {this.holder.getConfigAttribute().get(0).getTargetClass()}, new JdkProxy(holder));
  }
}

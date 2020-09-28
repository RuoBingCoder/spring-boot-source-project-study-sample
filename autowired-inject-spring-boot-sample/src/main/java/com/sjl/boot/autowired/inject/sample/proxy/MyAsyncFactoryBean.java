package com.sjl.boot.autowired.inject.sample.proxy;

import com.sjl.boot.autowired.inject.sample.bean.MyAsyncHolder;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:46 上午
 * @description: MyAsyncFactoryBean
 */
public class MyAsyncFactoryBean implements FactoryBean {

  private final MyAsyncHolder holder;

  public MyAsyncFactoryBean(MyAsyncHolder holder) {
    this.holder = holder;
  }

  @Override
  public Object getObject() throws Exception {
    InvocationHandler handler = new MyAsyncProxy(holder);
    return Proxy.newProxyInstance(
        holder.getBean().getClassLoader(),
        new Class[] {holder.getBean().getInterfaces()[0]},
        handler);
  }

  @Override
  public Class<?> getObjectType() {
    return holder.getBean();
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}

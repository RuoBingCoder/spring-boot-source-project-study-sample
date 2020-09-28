package com.sjl.boot.autowired.inject.sample.proxy;

import com.sjl.boot.autowired.inject.sample.bean.MyAsyncHolder;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> implements FactoryBean<T> {

  private final Class<T> interfaceType;
  private final boolean isAsync;
  private final MyAsyncHolder holder;

  @Override
  public T getObject() {
    if (!isAsync){
      InvocationHandler handler = new RpcServiceProxy<>(interfaceType);
      return (T)
              Proxy.newProxyInstance(
                      interfaceType.getClassLoader(), new Class[] {interfaceType}, handler);
    }
    InvocationHandler handler = new MyAsyncProxy(holder);
    return (T)
            Proxy.newProxyInstance(
                    holder.getBean().getInterfaces()[0].getClassLoader(), new Class[] {holder.getBean().getInterfaces()[0]}, handler);

  }

  public ProxyFactory(Class<T> interfaceType, boolean isAsync, MyAsyncHolder holder) {
    this.interfaceType = interfaceType;
    this.isAsync = isAsync;
    this.holder = holder;
  }



  @Override
  public Class<T> getObjectType() {
    return interfaceType;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}

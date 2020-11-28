package com.sjl.boot.autowired.inject.sample.proxy;

import com.sjl.boot.autowired.inject.sample.bean.MyAsyncHolder;
import com.sjl.boot.autowired.inject.sample.interceptor.CglibMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:46 上午
 * @description: MyAsyncFactoryBean
 */
@Slf4j
public class ProxyFactory implements FactoryBean {

    private final MyAsyncHolder holder;

    public static String proxy;
    private static final Enhancer en = new Enhancer();

    public ProxyFactory(MyAsyncHolder holder) {
        this.holder = holder;
    }

    @Override
    public Object getObject() {
        if (com.sjl.boot.autowired.inject.sample.constant.Proxy.CGLIB.equals(proxy)) {
            if (log.isTraceEnabled()){
                log.info("cglib proxy trace!");
            }
            log.info("开始cglib代理开始:{}",proxy);
            en.setSuperclass(holder.getBean());
            en.setCallback(new CglibMethodInterceptor(holder));
            return en.create();
        }
        InvocationHandler handler = new JdkDynamicHandler(holder);
        return Proxy.newProxyInstance(
                holder.getBean().getClassLoader(),
                new Class[]{holder.getBean().getInterfaces()[0]},
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

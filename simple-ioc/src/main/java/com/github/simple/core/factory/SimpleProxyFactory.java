package com.github.simple.core.factory;

import com.github.simple.core.interceptor.CglibMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 11:46 上午
 * @description: SimpleProxyFactory
 */

public class SimpleProxyFactory implements SimpleProxy {
    private static final Enhancer en = new Enhancer();

    @Override
    public <T> T createCGLIBProxy(Class<?> clazz) {
        return getProxy().createProxy(clazz);
    }

    private <T> T createProxy(Class<?> clazz) {
        en.setSuperclass(clazz);
        en.setCallback(new CglibMethodInterceptor());
        return (T) en.create();
    }

    public static SimpleProxyFactory getProxy() {
        return new SimpleProxyFactory();
    }
}

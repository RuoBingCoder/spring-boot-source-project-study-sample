package com.github.spring.components.cglib.proxy;

import com.github.spring.components.cglib.MyMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author jianlei.shi
 * @date: 2020/12/16 11:46 上午
 * @description: SimpleProxyFactory
 */

public class MyProxyFactory  implements Proxy{
    private static final Enhancer en = new Enhancer();

    public <T> T createCGLIBProxy(Class<?> clazz) {
        return getProxy().createProxy(clazz);
    }

    private <T> T createProxy(Class<?> clazz) {
        en.setSuperclass(clazz);
        en.setCallback(new MyMethodInterceptor());
        en.setCallbackFilter(method -> 0);
        return (T) en.create();
    }

    public static MyProxyFactory getProxy() {
        return new MyProxyFactory();
    }

    @Override
    public <T> T createProxy(ClassLoader classLoader) {

        return null;
    }
}

package utils;

import exception.CommonException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author jianlei.shi
 * @date 2021/2/25 11:10 上午
 * @description ProxyFactory
 */

public class ProxyFactory {

    private static final ProxyFactory proxyFactory = getProxyFactory();

    public static Object getProxy(Class<?> taClass, InvocationHandler handler) {
        return proxyFactory.newProxy(taClass, handler);
    }

    private static ProxyFactory getProxyFactory() {
        return new ProxyFactory();
    }

    public Object newProxy(Class<?> taClass, InvocationHandler handler) {
        final Object newProxy = Proxy.newProxyInstance(taClass.getClassLoader(), new Class[]{taClass}, handler);
        if ("null".equals(newProxy)) {
            throw new CommonException("proxy is null");
        }
        return newProxy;
    }


}

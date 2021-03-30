package com.github.factory;

import com.github.enums.ProxyType;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author jianlei.shi
 * @date 2021/3/10 5:39 下午
 * @description ProxyFactory
 * <pre>
 *     使用此类需继承此类实现#getHandler()方法即可
 * </pre>
 */

public abstract class AbsProxyFactory implements RBProxyFactory {

    protected static final Enhancer en = new Enhancer();

    @Override
    public <T> T getProxy(ProxyType type, Class<T> interClass) {
        if (type == null || type == ProxyType.JDK) {
            return (T) createJDKProxy(interClass);
        }
        if (type == ProxyType.CGLIB) {
            return (T) createCGLIBProxy(interClass);
        }

        return null;
    }

    private Object createCGLIBProxy(Class<?> interClass) {
        en.setSuperclass(interClass);
        en.setCallback(getHandler());
        return  en.create();

    }

    private Object createJDKProxy(Class<?> interClass) {
        return  Proxy.newProxyInstance(interClass.getClassLoader(), new Class[]{interClass}, getHandler());
    }

    protected abstract <T> T getHandler();
}

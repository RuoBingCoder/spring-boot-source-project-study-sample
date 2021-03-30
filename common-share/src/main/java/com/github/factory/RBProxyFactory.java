package com.github.factory;


import com.github.enums.ProxyType;

/**
 * @author jianlei.shi
 * @date 2021/3/10 5:34 下午
 * @description: CommonProxy
 */
public interface RBProxyFactory {

    <T> T getProxy(ProxyType type, Class<T> interClass);
}

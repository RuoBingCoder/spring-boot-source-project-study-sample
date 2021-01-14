package com.github.spring.components.cglib.proxy;

/**
 * @author jianlei.shi
 * @date 2021/1/4 11:33 上午
 * @description: Proxy
 */
public interface Proxy {

   <T> T createProxy(ClassLoader classLoader);
}

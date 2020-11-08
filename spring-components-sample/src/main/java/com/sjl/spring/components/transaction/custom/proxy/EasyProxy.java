package com.sjl.spring.components.transaction.custom.proxy;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:15 上午
 * @description: EasyProxy
 */
public interface EasyProxy {


    <T> T createProxy(Class<T> clazz);
}

package com.github.simple.ioc.factory;

/**
 * @author: JianLei
 * @date: 2020/12/16 11:46 上午
 * @description: SimpleProxy
 */
public interface SimpleProxy {

    default <T> T createCGLIBProxy(Class<?> clazz) {
        return null;
    }

}

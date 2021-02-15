package com.github.spring.components.learning.cglib.test;

import com.github.spring.components.learning.cglib.ConfigMethodTest;
import com.github.spring.components.learning.cglib.proxy.MyProxyFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/1/3 11:45 上午
 * @description @Configuration 代理原理
 * @see Configuration
 * @see BeanConfig
 */
public class Test {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object cglibProxy = MyProxyFactory.getProxy().createCGLIBProxy(ConfigMethodTest.class);
        Method method2 = ConfigMethodTest.class.getMethod("method2");
        Object invoke = method2.invoke(cglibProxy);

    }
}

package com.github.simple.demo.service.circular;

import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.beans.factory.SimpleBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/12 4:07 下午
 * @description: C
 */
@SimpleComponent
public class C {
    @SimpleAutowired
    private A a;
    @SimpleAutowired
    private B b;

    @SimpleAutowired
    private SimpleBeanFactory beanFactory;

    public void sendCMsg() throws Throwable {
        System.out.println("接收到 A 发来消息:");
        a.sendMsg();
        System.out.println("==>"+b.hello());
        System.out.println("************* B beanFactory is :"+beanFactory);
    }



}

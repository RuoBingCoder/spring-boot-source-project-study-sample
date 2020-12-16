package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleComponent;

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


    public void sendCMsg(){
        System.out.println("接收到 A 发来消息:");
        a.sendMsg();
        System.out.println("==>"+b.hello());
    }



}

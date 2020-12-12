package com.github.simple.demo.service;

import com.github.simple.ioc.annotation.SimpleAutowired;
import com.github.simple.ioc.annotation.SimpleComponent;

/**
 * @author: JianLei
 * @date: 2020/12/12 4:07 下午
 * @description: B
 */
@SimpleComponent
public class B {
    @SimpleAutowired
    private A a;
    public String hello() {

        a.sendMsg();
        return "this B msg";
    }


}

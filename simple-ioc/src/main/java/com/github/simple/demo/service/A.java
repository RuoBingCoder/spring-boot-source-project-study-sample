package com.github.simple.demo.service;

import com.github.simple.ioc.annotation.SimpleAutowired;
import com.github.simple.ioc.annotation.SimpleComponent;

import java.util.Arrays;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/12 4:07 下午
 * @description: A
 */
@SimpleComponent
public class A {

    @SimpleAutowired
    private B b;

    public void sendMsg() {
        System.out.println("this is a send msg");
    }

    public List<String> tasks() {
        System.out.println(b.hello());
        return Arrays.asList("数学", "英语", "语文");
    }
}

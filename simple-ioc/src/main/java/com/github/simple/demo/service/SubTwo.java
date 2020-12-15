package com.github.simple.demo.service;

import com.github.simple.ioc.annotation.SimpleComponent;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 4:31 下午
 * @description: SubTwo
 */
@SimpleComponent
public class SubTwo extends Parent implements Order{

    public void test1(){
        System.out.println("test1");
    }
}

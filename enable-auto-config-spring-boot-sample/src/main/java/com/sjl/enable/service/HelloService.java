package com.sjl.enable.service;

import com.sjl.enable.annotation.SimpleService;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: HelloService
 */
@SimpleService
public class HelloService {

    public String say(){
        System.out.println("hello spring boot!");
        return "hello spring boot";
    }
}

package com.sjl.enable.service;

import com.sjl.enable.annotation.SjlService;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: HelloService
 */
@SjlService
public class HelloService {

    public String say(){
        System.out.println("hello spring boot!");
        return "hello spring boot";
    }
}

package com.github.simple.demo.service;

import com.github.simple.demo.service.aop.LogService;
import com.github.simple.ioc.annotation.SimpleAutowired;
import com.github.simple.ioc.annotation.SimpleComponent;

/**
 * @author: JianLei
 * @date: 2020/12/12 4:07 下午
 * @description: 循环依赖测试bean
 */
@SimpleComponent
public class B {
    @SimpleAutowired
    private A a;
    @SimpleAutowired
    private LogService logService;
    public String hello() {
        a.sendMsg();
        System.out.println("***************testLog output :"+logService.testLog());
        return "this B msg";
    }


}

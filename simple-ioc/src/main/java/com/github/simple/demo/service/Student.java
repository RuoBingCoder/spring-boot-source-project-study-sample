package com.github.simple.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleComponent;

/**
 * @author: JianLei
 * @date: 2020/12/12 4:56 下午
 * @description: Student
 */
@SimpleComponent
public class Student {

    @SimpleAutowired
    private A a;
    @SimpleAutowired
    private B b;
    public String info(){
        System.out.println("a out:"+JSONObject.toJSONString(a.tasks()));
        System.out.println("==> b out :"+b.hello());
        return "123";
    }
}

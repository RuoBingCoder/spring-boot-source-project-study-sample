package com.github.simple.demo.service;

import com.github.simple.ioc.annotation.SimpleAutowired;
import com.github.simple.ioc.annotation.SimpleService;

/**
 * @author: JianLei
 * @date: 2020/12/12 11:09 下午
 * @description: Teacher
 */
@SimpleService
public class Teacher {

    @SimpleAutowired
    private Student student;

    public void getTeacher(String name){
        System.out.println("==> getTeacher name:"+name+"->student info: "+student.info());
    }


}

package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleService;

/**
 * @author: JianLei
 * @date: 2020/12/12 11:09 下午
 * @description: Teacher
 */
@SimpleService
public class Teacher {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {
    }

    @SimpleAutowired
    private Student student;

    public void getTeacher(String name){
        System.out.println("==> getTeacher name:"+name+"->student info: "+student.info());
    }




}

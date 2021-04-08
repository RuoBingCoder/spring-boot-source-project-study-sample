package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleService;
import com.github.simple.core.annotation.SimpleValue;
import com.github.simple.core.init.SimpleInitializingBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: JianLei
 * @date: 2020/12/12 11:09 下午
 * @description: Teacher
 */
@SimpleService
@Slf4j
public class Teacher implements SimpleInitializingBean {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {
    }

    @SimpleAutowired
    private Student student;

    @SimpleValue("word")
    private String hello;

    public void getTeacher(String name){
        System.out.println("==> getTeacher name:"+name+"->student info: "+student.info()+"hello->"+hello);
    }


    @Override
    public void afterPropertiesSet() {
        log.info("==>🪐🪐🪐🪐🪐🪐🪐🪐🪐🪐🪐🪐🪐student info:{} hello:{} ",student.info(),hello);

    }
}

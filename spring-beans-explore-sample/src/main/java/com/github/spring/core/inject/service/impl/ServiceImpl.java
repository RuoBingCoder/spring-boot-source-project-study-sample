package com.github.spring.core.inject.service.impl;


import com.github.spring.core.inject.service.Service;

/**
 * @author: JianLei
 * @date: 2020/9/8 7:09 下午
 * @description:
 */
@org.springframework.stereotype.Service("serviceImpl")
public class ServiceImpl implements Service {
    @Override
    public void doSave() {
        System.out.println("=============doSave()===============");
    }
}

package com.sjl.spring.components.service;

import org.springframework.stereotype.Component;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 5:38 下午
 * @description: LazyService
 */
@Component
public class LazyService {

    public LazyService() {
        System.out.println("==========LazyService init============");
    }

    public void testLazy(){
        System.out.println("=====lazy=====");

    }
}

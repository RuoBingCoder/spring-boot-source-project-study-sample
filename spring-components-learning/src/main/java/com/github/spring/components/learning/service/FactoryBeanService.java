package com.github.spring.components.learning.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 5:38 下午
 * @description: LazyService
 */
@Slf4j
public class FactoryBeanService {

    public FactoryBeanService() {
        log.info("FactoryBeanService init");
    }

    public void testOutput(){
        System.out.println("=====FactoryBeanService=====");

    }
}

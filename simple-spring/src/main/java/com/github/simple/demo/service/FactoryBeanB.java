package com.github.simple.demo.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/19 4:04 下午
 * @description: FactoryBean
 */
@Slf4j
public class FactoryBeanB {
    public FactoryBeanB() {
        log.info("FactoryBean B 构造器初始化 !");

    }

    public void testInit() {
        log.info("FactoryBean B testInit !");
    }
}

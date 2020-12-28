package com.github.spring.core.controller;


import com.github.spring.core.annotation.MyAutowired;
import com.github.spring.core.constants.Constant;
import com.github.spring.core.inject.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/9/8 7:34 下午
 * @description:
 */
@RestController
@RequestMapping("/")
@Slf4j
public class TestController {

    @MyAutowired(value = "service")
    private Service service;
    @Value("${env.server.name}")
    private String envServerName;

    @GetMapping(value = "/service", name = "service")
    public String test() throws InterruptedException {

        log.info("=====>get env server name is:{}", envServerName);
//    service.doSave();
        System.out.println("====>睡眠唤醒当前线程是:" + Thread.currentThread().getName());
        System.out.println("beans is:" + Constant.beansMap.size());
        return Thread.currentThread().getName();
    }

}

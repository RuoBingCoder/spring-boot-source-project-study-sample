package com.github.spring.dependency.inject.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.spring.dependency.inject.demo.annotation.CustomAutowired;
import com.github.spring.dependency.inject.demo.annotation.CustomValue;
import com.github.spring.dependency.inject.demo.service.HelloService;
import com.github.spring.dependency.inject.demo.service.IMyAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:57 上午
 * @description:
 */
@RestController
@Slf4j
public class TestController {

//  @MyAutowired(group = "101", version = "1.0.0")
//  private AutowiredService autowiredService;

    @CustomValue("${app.name}")
    private String appName;

    @CustomAutowired
    private IMyAsync myAsync;

    @CustomAutowired
    private HelloService helloService;

    @GetMapping(value = "/auto", name = "true")
    @ResponseBody
    public void auto() {
        System.out.println("===>app name is: " + appName);
//    autowiredService.sayToService();

    }

    @GetMapping(value = "/async", name = "true")
    public void async() {
        log.info("开始订单查询异步任务开始");
        myAsync.startAsync("订单查询");
        log.info("开始订单查询异步任务结束");

//    myAsync.startSync("物流查询");

    }

    @GetMapping(value = "/sayHello", name = "true")
    public void sayHello() {
        String hello = helloService.sayHello();
        log.info("==>:{}", JSONObject.toJSONString(hello));
//    myAsync.startSync("物流查询");

    }
}

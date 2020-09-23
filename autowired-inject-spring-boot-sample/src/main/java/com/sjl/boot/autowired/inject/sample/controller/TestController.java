package com.sjl.boot.autowired.inject.sample.controller;

import com.sjl.boot.autowired.inject.sample.annotation.MyAutowired;
import com.sjl.boot.autowired.inject.sample.service.AutowiredService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:57 上午
 * @description:
 */
@RestController
public class TestController {

    @MyAutowired(group = "101",version = "1.0.0")
    private AutowiredService autowiredService;

    @GetMapping(value = "/auto", name = "true")
    @ResponseBody
    public void auto(){
        autowiredService.sayToService();

    }

}

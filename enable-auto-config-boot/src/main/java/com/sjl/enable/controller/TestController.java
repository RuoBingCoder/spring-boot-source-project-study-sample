package com.sjl.enable.controller;

import com.sjl.enable.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: TestController
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private HelloService helloService;


    @GetMapping("/test")
    public String test(){
        System.out.println(helloService.say());
        return helloService.say();

    }
}

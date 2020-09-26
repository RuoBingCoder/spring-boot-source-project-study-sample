package com.sjl.custom.aop.controller;

import com.sjl.custom.aop.annotation.MyAutowired;
import com.sjl.custom.aop.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/9/22 7:26 下午
 * @description:
 */
@RestController
@RequestMapping
public class AopController {

  @MyAutowired
  private HelloService helloService;

  @GetMapping("/hello")
  public String hello() {
    //    helloService.sayHello("这是测试");
    System.out.println("======================");
    String list = helloService.list(1, 10, "张三");
    return "success";
  }
}

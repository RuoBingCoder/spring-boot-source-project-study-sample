package com.sjl.custom.aop.controller;

import com.sjl.custom.aop.annotation.MyAutowired;
import com.sjl.custom.aop.service.CglibService;
import com.sjl.custom.aop.service.HelloService;
import com.sjl.custom.aop.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: JianLei
 * @date: 2020/9/22 7:26 下午
 * @description:
 */
@RestController
@RequestMapping
public class AopController {

  @MyAutowired private HelloService helloService;


  @GetMapping("/hello")
  public String hello() {
    //    helloService.sayHello("这是测试");
    System.out.println("======================");
    String list = helloService.list(1, 10, "张三");
    // 测试cglib代理
    CglibService cglibService = SpringBeanUtil.getContext().getBean(CglibService.class);
    cglibService.selectOrderById(10001);
    return "success";
  }
}

package com.sjl.bean.life.cycle.controller;

import com.sjl.bean.life.cycle.annotation.MyAutowired;
import com.sjl.bean.life.cycle.inject.service.Service;
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
public class TestController {

  @MyAutowired(value = "service")
  private Service service;



  @GetMapping(value = "/service", name = "service")
  public String test() throws InterruptedException {
//    service.doSave();
    System.out.println("====>睡眠唤醒当前线程是:" + Thread.currentThread().getName());
    return Thread.currentThread().getName();
  }
}

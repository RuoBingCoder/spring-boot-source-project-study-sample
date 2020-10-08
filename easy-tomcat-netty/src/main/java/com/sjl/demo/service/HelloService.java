package com.sjl.demo.service;

import com.sjl.tomcat.core.annotation.EasyService;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:00 下午
 * @description: HelloService
 */
@EasyService
public class HelloService {

  public void sayHello() {
    System.out.println("hello word!");
  }
}

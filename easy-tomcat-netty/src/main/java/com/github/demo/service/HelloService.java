package com.github.demo.service;

import com.github.tomcat.core.annotation.EasyService;

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

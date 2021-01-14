package com.github.custom.aop.service;

/**
 * @author: JianLei
 * @date: 2020/9/22 7:21 下午
 * @description:
 */

public interface HelloService {

    void sayHello(String params);

    String list(Integer start,Integer end,String name);
}

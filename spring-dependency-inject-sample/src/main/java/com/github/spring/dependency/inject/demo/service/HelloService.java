package com.github.spring.dependency.inject.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/11/28 下午3:58
 * @description: HelloService
 */
@Service
public class HelloService {

    public String sayHello() {
        return "hello word!";
    }
}

package com.sjl.spring.circular.dependency.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/25 上午10:40
 * @description: A
 */
@Component
public class A {
    @Autowired
    private B b;
}

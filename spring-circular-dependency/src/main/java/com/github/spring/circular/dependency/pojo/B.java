package com.github.spring.circular.dependency.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/25 上午10:41
 * @description: B
 */
@Component
public class B {

    @Autowired
    private A a;

//    public B(A a) {
//        this.a = a;
//    }
}

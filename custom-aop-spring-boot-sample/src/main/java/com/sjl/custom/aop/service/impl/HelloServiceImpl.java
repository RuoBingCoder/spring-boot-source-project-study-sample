package com.sjl.custom.aop.service.impl;

import com.sjl.custom.aop.service.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/9/22 7:22 下午
 * @description:
 */
@Component("com.sjl.custom.aop.service.impl.HelloServiceImpl")
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String params) {
    System.out.println("----hello aop!----"+params);
    }

    @Override
    public String list(Integer start, Integer end, String name) {
    System.out.println("start:"+start+"end:"+end+"name:"+name);
        return "list";
    }
}

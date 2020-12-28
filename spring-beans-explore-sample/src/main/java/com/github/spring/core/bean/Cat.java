package com.github.spring.core.bean;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/11 4:45 下午
 * @description:
 */
@Component
public class Cat implements Ordered {




   /* @Autowired
    private Pig pig;*/

    static {
        System.out.println("*************Cat static************************");
    }

//    public Pig getPig() {
//        return pig;
//    }

    public String hello() {
        return "hello";
    }


    @Override
    public int getOrder() {
        return -200;
    }
}

package com.github.simple.demo.start;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.demo.service.Order;
import com.github.simple.demo.service.SubTwo;
import com.github.simple.ioc.factory.SimpleDefaultListableBeanFactory;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:47 下午
 * @description: StartIocApplication
 */

public class StartIocApplication {

    public static void main(String[] args) throws Throwable {
        SimpleDefaultListableBeanFactory beanFactory = new SimpleDefaultListableBeanFactory();
//        A a = beanFactory.getBean(A.class);
        SubTwo teacher = beanFactory.getBean(SubTwo.class);
        teacher.test1();
//        System.out.println(a.tasks());
        System.out.println("->beans size:"+beanFactory.getBeans().size());

        Map<String, Order> beans = beanFactory.getBeansOfType(Order.class);
        System.out.println("==>"+ JSONObject.toJSONString(beans));

    }
}

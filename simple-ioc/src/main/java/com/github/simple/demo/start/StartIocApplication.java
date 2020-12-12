package com.github.simple.demo.start;

import com.github.simple.demo.service.Teacher;
import com.github.simple.ioc.factory.SimpleDefaultListableBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:47 下午
 * @description: StartIocApplication
 */

public class StartIocApplication {

    public static void main(String[] args) throws Throwable {
        SimpleDefaultListableBeanFactory beanFactory = new SimpleDefaultListableBeanFactory();
//        A a = beanFactory.getBean(A.class);
        Teacher teacher = beanFactory.getBean(Teacher.class);
        teacher.getTeacher("张三");
//        System.out.println(a.tasks());
        System.out.println("->beans size:"+beanFactory.getBeans().size());

    }
}

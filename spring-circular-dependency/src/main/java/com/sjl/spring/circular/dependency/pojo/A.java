package com.sjl.spring.circular.dependency.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/25 上午10:40
 * @description: A
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#postProcessProperties
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency
 */
@Component
public class A {
    @Autowired
    private B b;

    /**
     * 构造器可以快速校验循环依赖 Spring官方推荐
     * @param b
     */
//    public A(B b) {
//        this.b = b;
//    }

    public void output(){
        System.out.println("=====>循环依赖 root class is A");
    }
}

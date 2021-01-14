package com.github.spring.components.cglib;

/**
 * @author jianlei.shi
 * @date 2021/1/3 11:43 上午
 * @description ConfigMethodTest
 */

public class ConfigMethodTest {
    
    public void method1(){
        System.out.println(" this is method1 test!");
    }

    public void method2(){
        method1();
        System.out.println(" this is method2 test!");
    }
}

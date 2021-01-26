package com.github.spring.components.learning.pojo;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/1/2 6:15 下午
 * @description ConfigBeanA
 * @see BeanConfig
 */

public class ConfigBeanB {

    public ConfigBeanB() {
        System.out.println("ConfigBeanB create...");

    }

    public void eat(){
        System.out.println("B eat !");
    }
    public void bHello(){
        eat();
        System.out.println("B say hello !");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ConfigBeanB configBeanB=new ConfigBeanB();
        Method method = ConfigBeanB.class.getMethod("bHello", null);
        method.invoke(configBeanB,null);
    }
}

package com.sjl.spring.components.pojo;

/**
 * @author jianlei.shi
 * @date 2021/1/2 6:15 下午
 * @description ConfigBeanA
 * @see com.sjl.spring.components.config.BeanConfig
 */

public class ConfigBeanA {

    public ConfigBeanA() {
        System.out.println("ConfigBeanA create...");
    }

    public void aHello(){
        System.out.println("A say hello !");
    }
}

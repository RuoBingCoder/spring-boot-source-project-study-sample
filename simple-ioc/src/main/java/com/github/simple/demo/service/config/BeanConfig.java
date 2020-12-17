package com.github.simple.demo.service.config;

import com.github.simple.core.annotation.SimpleBean;
import com.github.simple.core.annotation.SimpleConfig;
import com.github.simple.demo.service.ConfigBeanTest;
import com.github.simple.demo.service.ConfigBeanTest2;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:57 下午
 * @description: BeanConfig
 */
@SimpleConfig
public class BeanConfig {

    @SimpleBean
    public ConfigBeanTest configBeanTest() {
        return new ConfigBeanTest("beijing");
    }

    @SimpleBean
    public ConfigBeanTest2 configBeanTest2() {
        return new ConfigBeanTest2("configBeanTest2 info",1201L);
    }
}

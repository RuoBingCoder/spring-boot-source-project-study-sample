package com.github.simple.demo.service.config;

import com.github.simple.core.annotation.SimpleBean;
import com.github.simple.core.annotation.SimpleConfig;
import com.github.simple.core.annotation.SimpleValue;
import com.github.simple.demo.service.config.domain.MethodBean1;
import com.github.simple.demo.service.config.domain.MethodBean2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:57 下午
 * @description: test
 * {@link SimpleConfig}
 * {@link SimpleBean}
 */
@SimpleConfig
@Slf4j
public class BeanConfig {

    @SimpleValue("${simple.address}")
    private String address;

    @SimpleBean
    public MethodBean1 configBeanTest() {
        return new MethodBean1("beijing");
    }

    @SimpleBean
    public MethodBean2 configBeanTest2() {
        log.info("获取address value is:{}",address==null?" null ":address);
        return new MethodBean2(address,1201L);
    }
}

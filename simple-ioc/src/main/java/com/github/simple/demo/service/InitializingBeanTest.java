package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.init.SimpleInitializingBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 11:21 上午
 * @description: Init
 */
@SimpleComponent
@Slf4j
public class InitializingBeanTest implements SimpleInitializingBean {
    @Override
    public void afterPropertiesSet() {
        log.info("==>>Init method call back!");
    }
}

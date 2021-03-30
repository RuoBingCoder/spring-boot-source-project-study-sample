package com.github.simple.demo.service.circular;

import com.github.simple.core.annotation.SimpleAutowired;
import com.github.simple.core.annotation.SimpleComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 11:18 上午
 * @description: D
 */
@SimpleComponent
@Slf4j
public class D {

    @SimpleAutowired
    private String hello;
    public void dTest(){
        log.info("-->>>> d test!->"+hello);
    }
}

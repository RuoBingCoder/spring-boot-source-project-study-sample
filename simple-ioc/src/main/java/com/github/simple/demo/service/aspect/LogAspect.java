package com.github.simple.demo.service.aspect;

import com.github.simple.ioc.annotation.SimpleAspect;
import com.github.simple.ioc.annotation.SimpleComponent;
import com.github.simple.ioc.annotation.SimplePointCut;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 11:53 上午
 * @description: LogAspect
 */
@SimpleComponent
@SimpleAspect
public class LogAspect {

    @SimplePointCut(express = "com.github.simple.demo.service.aop")
    public void pointCut(){
    }
}

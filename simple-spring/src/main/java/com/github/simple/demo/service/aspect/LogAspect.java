package com.github.simple.demo.service.aspect;

import com.github.simple.core.annotation.SimpleAspect;
import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.annotation.SimplePointCut;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 11:53 上午
 * @description: aop切面
 */
@SimpleComponent
@SimpleAspect
public class LogAspect {

    @SimplePointCut(expression = "com.github.simple.demo.service.aop")
    public void pointCut(){
    }
}

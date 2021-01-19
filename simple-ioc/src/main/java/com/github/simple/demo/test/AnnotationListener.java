package com.github.simple.demo.test;

import com.github.simple.core.annotation.EventListener;
import com.github.simple.core.annotation.SimpleComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2021/1/19 5:36 下午
 * @description AnnotationListener
 */
@SimpleComponent
@Slf4j
public class AnnotationListener {
    
    @EventListener
    public void listener(MockEvent mockEvent){
        log.info("*********************>>>>mock listener :{}<<<*****************",mockEvent.getMsg());
    }
}

package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.context.SimpleApplicationListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2021/1/18 2:08 下午
 * @description ParperSimpleApplicationListener
 */
@Slf4j
@SimpleComponent
public class PrepareSimpleApplicationListener implements SimpleApplicationListener<SayHelloEvent> {

    @Override
    public void onApplicationEvent(SayHelloEvent event) {
        log.info("event say :{}",event.getMsg());
    }
}

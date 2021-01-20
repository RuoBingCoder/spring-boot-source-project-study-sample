package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.context.SimpleApplicationContext;
import com.github.simple.core.context.SimpleApplicationListener;
import com.github.simple.core.context.SimpleContextRefreshEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2021/1/20 10:22 上午
 * @description ContextRefreshListener
 */
@SimpleComponent
@Slf4j
public class ContextRefreshListener implements SimpleApplicationListener<SimpleContextRefreshEvent> {
    @Override
    public void onApplicationEvent(SimpleContextRefreshEvent event) {
        final SimpleApplicationContext applicationContext = event.getApplicationContext();
        log.info("ContextRefreshListener applicationContext :{}",applicationContext.getBeanFactory().toString());
    }
}

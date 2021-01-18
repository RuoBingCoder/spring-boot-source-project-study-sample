package com.github.simple.core.context;

import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2021/1/18 3:12 下午
 * @description EventMulticasterPostProcessor
 */
@Slf4j
public class SimpleListenerMulticasterPostProcessor implements SimpleBeanPostProcessor, SimpleApplicationContextAware {
    private SimpleApplicationContext simpleApplicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        if (bean instanceof SimpleApplicationListener) {
            SimpleApplicationListener simpleApplicationListener = (SimpleApplicationListener) bean;
            simpleApplicationContext.addApplicationListener(simpleApplicationListener);
        }
        return null;
    }

    @Override
    public void setApplicationContext(SimpleApplicationContext simpleApplicationContext) {
        log.info("开始获取SimpleApplicationContext->:{}",simpleApplicationContext==null?"null":simpleApplicationContext.getClass());
        this.simpleApplicationContext = simpleApplicationContext;
    }
}

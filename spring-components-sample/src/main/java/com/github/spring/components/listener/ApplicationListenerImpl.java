package com.github.spring.components.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.AbstractApplicationEventMulticaster;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/1/18 12:35 下午
 * @description ApplicationListenerImpl
 * @see org.springframework.context.event.SimpleApplicationEventMulticaster#multicastEvent(ApplicationEvent)
 * @see org.springframework.context.event.SimpleApplicationEventMulticaster#invokeListener
 * @see AbstractApplicationEventMulticaster#getApplicationListeners(ApplicationEvent, ResolvableType)
 */
@Component
@Slf4j
public class ApplicationListenerImpl implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()==null){
            log.info("ApplicationListenerImpl publish event!");
        }
    }
}

package com.github.spring.components.learning.listener.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/2/16 1:57 下午
 * @description SpringstartedApplicationListener
 * @since spring-boot 2.0 支持
 */
@Component
@Slf4j
public class SpringStartedApplicationListener {

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStarted(){
        log.info("SpringStartedApplicationListener event info!");
    }
}

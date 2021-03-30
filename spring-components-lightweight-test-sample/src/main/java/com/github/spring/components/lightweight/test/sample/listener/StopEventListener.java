package com.github.spring.components.lightweight.test.sample.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/3/18 8:18 下午
 * @description StartEventListener
 */
@Slf4j
@Component
public class StopEventListener implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        log.info("<StopEventListener> start event");
    }
}

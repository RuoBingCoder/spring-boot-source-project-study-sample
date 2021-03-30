package com.github.spring.components.lightweight.test.sample.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/3/18 8:18 下午
 * @description StartEventListener
 * 1
 */
@Slf4j
@Component
public class RefreshEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("<RefreshEventListener> start event");
    }
}

package com.github.spring.components.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author: JianLei
 * @date: 2020/11/4 10:20 上午
 * @description: CustomEvent
 */
@Slf4j
public class CustomEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public CustomEvent(Object source) {
        super(source);
    }
}

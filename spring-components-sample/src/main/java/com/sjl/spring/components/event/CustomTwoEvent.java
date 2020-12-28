package com.sjl.spring.components.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: JianLei
 * @date: 2020/11/4 10:50 上午
 * @description: CustomTwoEvent
 */
@Setter
@Getter
public class CustomTwoEvent extends ApplicationEvent {

    private String event;

    public CustomTwoEvent(Object source, String event) {
        super(source);
        this.event = event;
    }

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */




}

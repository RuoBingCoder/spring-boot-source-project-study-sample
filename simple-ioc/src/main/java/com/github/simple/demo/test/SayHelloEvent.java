package com.github.simple.demo.test;

import com.github.simple.core.context.SimpleApplicationEvent;
import lombok.Getter;

/**
 * @author jianlei.shi
 * @date 2021/1/18 3:06 下午
 * @description SayHelloEvent
 */
@Getter
public class SayHelloEvent extends SimpleApplicationEvent {
    
    private String msg;

    public SayHelloEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */

}

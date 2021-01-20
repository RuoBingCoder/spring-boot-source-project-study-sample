package com.github.simple.core.context;

/**
 * @author jianlei.shi
 * @date 2021/1/20 10:13 上午
 * @description 容器上下文刷新事件
 */

public class SimpleContextRefreshEvent extends SimpleApplicationEvent{
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public SimpleContextRefreshEvent(SimpleApplicationContext source) {
        super(source);
    }
    
    public SimpleApplicationContext getApplicationContext(){
        return (SimpleApplicationContext) source;
    }
}

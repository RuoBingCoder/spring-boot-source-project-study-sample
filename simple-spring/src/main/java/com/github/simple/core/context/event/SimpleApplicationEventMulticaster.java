package com.github.simple.core.context.event;

import com.github.simple.core.context.SimpleApplicationEvent;
import com.github.simple.core.context.SimpleApplicationListener;

/**
 * @author jianlei.shi
 * @date 2021/1/18 12:11 下午
 * @description: SimpleApplicationEventMulticaster
 */
public interface SimpleApplicationEventMulticaster {

    /**
     * 添加应用程序侦听器
     *
     * @param listener 侦听器
     */
    void addApplicationListener(SimpleApplicationListener<?> listener);

    /**
     * 多播事件
     *standard
     * @param event 事件
     */
    void multicastEvent(SimpleApplicationEvent event) throws Throwable;

}

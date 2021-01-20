package com.github.simple.core.context;

/**
 * 简单的应用程序事件发布者
 *
 * @author jianlei.shi
 * @date 2021/1/18 2:39 下午
 * @description: SimpleApplicationEventPublisher
 */
@FunctionalInterface
public interface SimpleApplicationEventPublisher {


    void publishEvent(SimpleApplicationEvent event) throws Throwable;

}

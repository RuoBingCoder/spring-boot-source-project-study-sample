package com.github.simple.core.context;

/**
 * @author jianlei.shi
 * @date 2021/1/18 2:39 下午
 * @description: SimpleApplicationEventPublisher
 */
@FunctionalInterface
public interface SimpleApplicationEventPublisher {


    void publishEvent(SimpleApplicationEvent event) throws Throwable;

}

package com.github.simple.core.context;

/**
 * 简单的应用程序侦听器
 *
 * @author jianlei.shi
 * @date 2021/1/18 12:58 下午
 * @description SimpleApplicationListener
 */

public interface SimpleApplicationListener<E extends  SimpleApplicationEvent> extends SimpleEventListener {


    void onApplicationEvent(E event) throws Throwable;

}

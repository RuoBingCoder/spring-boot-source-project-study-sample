package com.github.simple.core.context;

/**
 * @author jianlei.shi
 * @date 2021/1/18 12:58 下午
 * @description SimpleApplicationListener
 */

public interface SimpleApplicationListener<E extends  SimpleApplicationEvent> extends SimpleEventListener {


    void onApplicationEvent(E event);

}

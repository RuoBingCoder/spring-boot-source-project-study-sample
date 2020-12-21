package com.github.simple.core.context;

import com.github.simple.core.factory.SimpleBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/21 3:04 下午
 * @description: SimpleApplicationContext
 */
public interface SimpleConfigApplicationContext {


    SimpleBeanFactory getBeanFactory();


    void refresh() throws Throwable;



}

package com.github.simple.core.context;

import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleConfigBeanFactory;

/**
 * @author: JianLei
 * @date: 2020/12/21 3:04 下午
 * @description: SimpleApplicationContext
 */
public interface SimpleConfigApplicationContext extends SimpleConfigBeanFactory {


    SimpleBeanFactory getBeanFactory();


    void refresh() throws Throwable;



}

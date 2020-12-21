package com.github.simple.core.context;

import com.github.simple.core.factory.SimpleBeanFactory;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 4:27 下午
 * @description: SimpleApplicationContext
 */

public class SimpleApplicationContext extends AbsSimpleApplicationContext{


    public static SimpleApplicationContext run(Class<?> clazz) throws Throwable {
        return new SimpleApplicationContext(clazz);
    }

    protected SimpleApplicationContext(Class<?> sourceClass) throws Throwable {
        super(sourceClass);
    }

    @Override
    public SimpleBeanFactory getBeanFactory() {
        return super.getBeanFactory();
    }
}

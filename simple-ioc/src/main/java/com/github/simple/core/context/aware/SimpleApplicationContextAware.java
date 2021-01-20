package com.github.simple.core.context.aware;

import com.github.simple.core.context.SimpleApplicationContext;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:02 下午
 * @description: SimpleBeanFactoryAware
 */

public interface SimpleApplicationContextAware {


    void setApplicationContext(SimpleApplicationContext simpleBeanFactory);
}

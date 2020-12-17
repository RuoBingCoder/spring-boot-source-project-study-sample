package com.github.simple.core.exception;

/**
 * @author: JianLei
 * @date: 2020/12/12 11:32 下午
 * @description: BeanCreateException
 */

public class SimpleBeanDefinitionNotFoundException extends SimpleIOCBaseException{
    public SimpleBeanDefinitionNotFoundException(String msg) {
        super(msg);
    }
}

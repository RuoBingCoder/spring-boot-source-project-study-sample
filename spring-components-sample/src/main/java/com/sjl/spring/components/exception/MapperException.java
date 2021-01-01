package com.sjl.spring.components.exception;

/**
 * @author: JianLei
 * @date: 2020/11/7 8:24 下午
 * @description: BeansNotFoundException
 */

public class MapperException extends RuntimeException{
    public MapperException(String message) {
        super(message);
    }
}

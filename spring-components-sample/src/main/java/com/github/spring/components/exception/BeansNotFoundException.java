package com.github.spring.components.exception;

/**
 * @author: JianLei
 * @date: 2020/11/7 8:24 下午
 * @description: BeansNotFoundException
 */

public class BeansNotFoundException extends RuntimeException{
    public BeansNotFoundException(String message) {
        super(message);
    }
}

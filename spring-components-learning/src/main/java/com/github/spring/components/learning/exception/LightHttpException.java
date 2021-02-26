package com.github.spring.components.learning.exception;

/**
 * @author: JianLei
 * @date: 2020/11/7 8:24 下午
 * @description: LightHttpException
 */

public class LightHttpException extends RuntimeException{
    public LightHttpException(String message) {
        super(message);
    }
}

package com.github.simple.core.utils;

/**
 * @author: JianLei
 * @date: 2020/12/23 4:08 下午
 * @description: SimpleStringValueResolver
 */
@FunctionalInterface
public interface SimpleStringValueResolver {

    String resolveStringValue(String strVal) throws Throwable;
}

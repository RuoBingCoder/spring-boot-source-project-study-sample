package com.github.spring.components.service;

/**
 * @author: JianLei
 * @date: 2020/11/24 下午4:58
 * @description: SearchService
 */
@FunctionalInterface
public interface SearchService {

    String query(String key);

}

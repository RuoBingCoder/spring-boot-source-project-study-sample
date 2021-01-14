package com.github.spring.components.service;

import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/11/24 下午5:00
 * @description: OperateService
 */
@Service
public class OperateService {

    public static String add(String id, SearchService searchService) {
        return searchService.query(id);

    }

    public static void main(String[] args) {
        System.out.println(add("123", key -> "abc->" + key));
    }
}

package com.github.simple.demo.service.aop;

import com.github.simple.core.annotation.SimpleComponent;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 11:55 上午
 * @description: LogService
 */
@SimpleComponent
public class LogService {

    public String testLog(){
        return "log info testLog";
    }

    public static void main(String[] args) {
        String str="com.github.simple.demo.service.aop";
        System.out.println(LogService.class.getName());
        System.out.println(LogService.class.getName().startsWith(str));
    }
}

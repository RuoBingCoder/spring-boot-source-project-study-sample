package com.sjl.boot.autowired.inject.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/11/28 上午10:40
 * @description: SpringScheduledService
 */
@Service
@Slf4j
public class SpringScheduledService {

    @Async
    public void log(){
        log.info("****current thread name is:{}",Thread.currentThread().getName());
    }
}

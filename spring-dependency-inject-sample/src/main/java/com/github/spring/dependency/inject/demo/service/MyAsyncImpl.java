package com.github.spring.dependency.inject.demo.service;

import com.github.spring.dependency.inject.demo.annotation.CustomAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:57 上午
 * @description: MyAsyncImpl
 */
@Component
@Slf4j
public class MyAsyncImpl implements IMyAsync {

    @Override
    @CustomAsync
    public void startAsync(String task) {
        log.info("异步任务service:{}当前线程是:{}", task, Thread.currentThread().getName());
    }

    @Override
    public void startSync(String task) {
        log.info("同步任务service:{}当前线程是:{}", task, Thread.currentThread().getName());


    }
}

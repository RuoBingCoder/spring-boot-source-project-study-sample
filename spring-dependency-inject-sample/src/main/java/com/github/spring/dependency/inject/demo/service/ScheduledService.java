package com.github.spring.dependency.inject.demo.service;

import com.github.spring.dependency.inject.demo.annotation.CustomScheduled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author: JianLei
 * @date: 2020/9/24 11:17 上午
 * @description:
 */
@Component
@Slf4j
public class ScheduledService {

    @CustomScheduled(cron = "0 10")
    @Scheduled
    public void scheduledTask(){
        log.info("->开始执行定时任务当前时间是:{}", Instant.now());
    }
}

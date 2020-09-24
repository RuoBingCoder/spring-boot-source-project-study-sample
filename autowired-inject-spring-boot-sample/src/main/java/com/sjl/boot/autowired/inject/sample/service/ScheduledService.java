package com.sjl.boot.autowired.inject.sample.service;

import com.sjl.boot.autowired.inject.sample.annotation.MyScheduled;
import lombok.extern.slf4j.Slf4j;
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

    @MyScheduled(cron = "0 10")
    public void scheduledTask(){
        log.info("->开始执行定时任务当前时间是:{}", Instant.now());
    }
}

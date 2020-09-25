package com.sjl.enable.config;

import com.sjl.enable.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/24 6:39 下午
 * @description:
 */
@Component
public class ScheduledConfig {

    @Autowired
    private ScheduledService scheduledService;

    @Scheduled(cron = "*/5 * * * * *")
    public void doScheduled(){
        scheduledService.scheduledTask();

    }
}

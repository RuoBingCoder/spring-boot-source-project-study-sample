package com.sjl.enable.service;

import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author: JianLei
 * @date: 2020/9/24 6:38 下午
 * @description:
 */
@Component
public class ScheduledServiceImpl implements ScheduledService{
    @Override
    public void scheduledTask() {
    System.out.println("====>>:"+ Instant.now());
    }
}

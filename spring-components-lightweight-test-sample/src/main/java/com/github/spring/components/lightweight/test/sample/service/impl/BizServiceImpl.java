package com.github.spring.components.lightweight.test.sample.service.impl;

import com.github.helper.ThreadPoolHelper;
import com.github.http.ModelResult;
import com.github.spring.components.lightweight.test.sample.monitor.CacheStore;
import com.github.spring.components.lightweight.test.sample.service.BizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianlei.shi
 * @date 2021/2/16 7:30 下午
 * @description BizServiceImpl
 */
@Service
@Slf4j
public class BizServiceImpl implements BizService, InitializingBean {
    @Autowired
    private ThreadPoolHelper threadPoolHelper;
    private String appName;
    @Autowired
    private CacheStore cacheStore;
    @Override
    public ModelResult<String> getAppName() {
        ModelResult<String> result = new ModelResult<>();
        result.setData(appName == null ? "QQ" : appName);
        return result;
    }


    @Override
    public void afterPropertiesSet() {
        AtomicInteger ai = new AtomicInteger();
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) threadPoolHelper.getExecutor(enums.ThreadTypeEnum.SCHEDULED, false);
        cacheStore.init(executor);
        executor.scheduleWithFixedDelay(() -> {
            appName = "淘宝" + ai.getAndIncrement();
            log.info("开始随机生成app name:{}",appName);
        }, 2, 10, TimeUnit.SECONDS);
    }
}

package com.github.spring.components.lightweight.test.sample.job;

import com.alibaba.fastjson.JSONObject;
import enums.ThreadTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import helper.ThreadPoolHelper;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jianlei.shi
 * @date 2021/2/7 2:48 下午
 * @description JobHandler
 */
@Component
@Slf4j
public class JobHandler implements InitializingBean {
    @Autowired
    private TaskWrapper tw;
    @Autowired
    private ThreadPoolHelper threadPoolHelper;
    private final ReentrantLock lock = new ReentrantLock();

    public void handle(Integer time) {
        lock.lock();
        ScheduledThreadPoolExecutor stp = (ScheduledThreadPoolExecutor) threadPoolHelper.getExecutor(ThreadTypeEnum.SCHEDULED, true);
        log.info("----------------开始定时任务执行task---------------");
        try {
            doExeTask(stp);
        } catch (Exception e) {
            log.error("job handle exception", e);
            throw new RuntimeException("job handle exception");
        } finally {
            lock.unlock();
        }
    }

    private void doExeTask(ScheduledThreadPoolExecutor stp) {
//        log.info("----------------开始定时任务执行task---------------");
        stp.scheduleAtFixedRate(() -> {
            try {
//                while (!isStop) {
                    final Task take = tw.take();
                    log.info("====>>>>result : {}", JSONObject.toJSONString(take));
//                }
            } catch (InterruptedException e) {
                log.error("thread Interrupted -> doExeTask error", e);
                throw new RuntimeException("job handle exception");

            }
        }, 20, 2, TimeUnit.SECONDS);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handle(null);
    }
}

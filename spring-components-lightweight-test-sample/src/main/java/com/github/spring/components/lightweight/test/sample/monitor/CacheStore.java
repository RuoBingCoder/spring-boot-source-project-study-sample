package com.github.spring.components.lightweight.test.sample.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

/**
 * @author jianlei.shi
 * @date 2021/2/21 5:23 下午
 * @description CacheStore
 */
@Slf4j
@Component
public class CacheStore implements Runnable {

    private ThreadPoolExecutor executor;
    private volatile Boolean isStop = false;


    public void init(ThreadPoolExecutor executor) {
        this.executor=executor;
        new Thread(this, "cache-thread-pool").start();
    }

    @Override
    public void run() {
        while (!isStop && !Thread.currentThread().isInterrupted()) {
            try {
                sleep(10000);
                log.info("====>>>current thread info core :{} max:{} queue size:{} task count: {} active count:{}",executor.getCorePoolSize(),executor.getPoolSize(),executor.getQueue().size(),executor.getTaskCount(),executor.getActiveCount());
            } catch (Exception e) {
                isStop=true;
                log.error("cache-thread-pool monitor error",e);
                throw new RuntimeException("cache-thread-pool monitor error");
            }
        }
    }
}

package com.github.spring.components.learning.lighthttp.store;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jianlei.shi
 * @date 2021/2/26 11:56 上午
 * @description lighthttp 线程池监控store
 * @since 1.0.0
 */
@Component
public class LightHttpStore {

    private Set<ThreadPoolExecutor> threads = new HashSet<>();

    public Set<ThreadPoolExecutor> getThreads() {
        return threads;
    }


    public void addThreads(ThreadPoolExecutor threadPoolExecutor) {
        this.threads.add(threadPoolExecutor);
    }

    public void setThreads(Set<ThreadPoolExecutor> threads) {
        this.threads = threads;
    }
}

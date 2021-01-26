package com.github.custom.config;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: JianLei
 * @date: 2020/9/17 5:42 下午
 * @description:
 */

public class ThreadPoolHolder {

    private ThreadPoolExecutor threadPool;

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }
}

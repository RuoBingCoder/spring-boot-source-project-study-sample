package com.sjl.boot.autowired.inject.sample.utils;

import com.sjl.boot.autowired.inject.sample.bean.CronScheduled;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: JianLei
 * @date: 2020/9/23 8:09 下午
 * @description:
 */

public class ThreadPoolUtil {
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = this.defaultFactory.newThread(r);
            if (!thread.isDaemon()) {
                thread.setDaemon(true);
            }

            thread.setName("MyScheduled-" + this.threadNumber.getAndIncrement());
            return thread;
        }
    };
    public static void scheduledTask(CronScheduled cronScheduled){
        String[] s = cronScheduled.getCron().split(" ");
        ScheduledExecutorService threadPoolExecutor= Executors.newScheduledThreadPool(4,THREAD_FACTORY);
        threadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                cronScheduled.getMethod().invoke(cronScheduled.getBean());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        },Long.parseLong(s[0]),Long.parseLong(s[1]), TimeUnit.SECONDS);
    }
}

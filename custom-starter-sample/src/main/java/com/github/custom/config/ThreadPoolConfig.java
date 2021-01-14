package com.github.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: JianLei
 * @date: 2020/9/17 3:51 下午
 * @description:
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolPropertiesConfig.class)
public class ThreadPoolConfig {

  @Autowired private ThreadPoolPropertiesConfig threadPoolPropertiesConfig;

  @Bean(name = "threadPoolHolder")
  public ThreadPoolHolder threadPoolExecutor() {

      ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
              threadPoolPropertiesConfig.getCoreSize(), threadPoolPropertiesConfig.getMaxSize(),
              threadPoolPropertiesConfig.getKeepAliveTime(),
              TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(),
              new ThreadFactory() {
                  final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
                  final AtomicInteger threadNumber = new AtomicInteger(1);

                  @Override
                  public Thread newThread(Runnable r) {
                      Thread thread = this.defaultFactory.newThread(r);
                      if (!thread.isDaemon()) {
                          thread.setDaemon(true);
                      }
                      thread.setName("custom-thread-pool-" + this.threadNumber.getAndIncrement());
                      return thread;
                  }
              });
      ThreadPoolHolder poolHolder=new ThreadPoolHolder();
      poolHolder.setThreadPool(threadPoolExecutor);
      return poolHolder;

  }
}

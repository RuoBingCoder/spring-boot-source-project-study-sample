package com.sjl.boot.autowired.inject.sample.service;

import com.sjl.boot.autowired.inject.sample.annotation.MyAsync;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:57 上午
 * @description: MyAsyncImpl
 */
@Component
public class MyAsyncImpl implements IMyAsync {

  @Override
  @MyAsync
  public void startAsync(String task) {
    System.out.println("异步任务service" + task+"当前线程是: "+Thread.currentThread().getName());
  }

  @Override
  public void startSync(String task) {
    System.out.println("同步任务service" + task+"当前线程是: "+Thread.currentThread().getName());

  }
}

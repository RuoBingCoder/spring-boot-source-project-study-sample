package com.sjl.boot.autowired.inject.sample.controller;

import com.sjl.boot.autowired.inject.sample.annotation.MyAutowired;
import com.sjl.boot.autowired.inject.sample.annotation.MyValue;
import com.sjl.boot.autowired.inject.sample.service.AutowiredService;
import com.sjl.boot.autowired.inject.sample.service.IMyAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:57 上午
 * @description:
 */
@RestController
@Slf4j
public class TestController {

//  @MyAutowired(group = "101", version = "1.0.0")
//  private AutowiredService autowiredService;

  @MyValue("${app.name}")
  private String appName;

  @MyAutowired
  private IMyAsync myAsync;

  @GetMapping(value = "/auto", name = "true")
  @ResponseBody
  public void auto() {
    System.out.println("===>app name is: "+appName);
//    autowiredService.sayToService();

  }
  @GetMapping(value = "/async", name = "true")
  @ResponseBody
  public void async() {
    log.info("开始订单查询异步任务开始");
    myAsync.startAsync("订单查询");
    log.info("开始订单查询异步任务结束");

//    myAsync.startSync("物流查询");

  }
}

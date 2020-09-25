package com.sjl.enable.controller;

import com.sjl.custom.config.ThreadPoolHolder;
import com.sjl.custom.config.ThreadPoolPropertiesConfig;
import com.sjl.enable.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: TestController
 */
@RestController
@RequestMapping("/")
public class TestController {

  @Autowired private HelloService helloService;

  @Resource private ThreadPoolHolder threadPoolHolder;
  @GetMapping("/test")
  public String test() {
    System.out.println(helloService.say());
    ThreadPoolExecutor threadPoolExecutor = threadPoolHolder.getThreadPool();
    threadPoolExecutor.execute(
        () -> System.out.println("=======>" + Thread.currentThread().getName()));
    return helloService.say();
  }



}

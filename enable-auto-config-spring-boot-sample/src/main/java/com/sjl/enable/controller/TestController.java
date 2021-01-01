package com.sjl.enable.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: TestController
 */
@RestController
@RequestMapping("/")
public class TestController {

//  @Autowired private HelloService helloService;

  @Value("${com.github.info}")
  private String info;


 /* @Resource private ThreadPoolHolder threadPoolHolder;
  @GetMapping("/test")
  public String test() {
    System.out.println(helloService.say());
    ThreadPoolExecutor threadPoolExecutor = threadPoolHolder.getThreadPool();
    threadPoolExecutor.execute(
        () -> System.out.println("=======>" + Thread.currentThread().getName()));
    return helloService.say();
  }*/



}

package com.github.custom.aop;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = RocketMQAutoConfiguration.class)
public class CustomAopSpringBootSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomAopSpringBootSampleApplication.class, args);
  }
}

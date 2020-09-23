package com.sjl.bean.life.cycle;

import com.sjl.bean.life.cycle.inject.service.impl.ServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeanLifeCycleApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext run =
        SpringApplication.run(BeanLifeCycleApplication.class, args);
//    run.getBean(ServiceImpl.class).doSave();
//    run.close();
  }
}

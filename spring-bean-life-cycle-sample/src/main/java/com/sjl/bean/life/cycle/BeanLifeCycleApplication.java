package com.sjl.bean.life.cycle;

import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sjl.bean.life.cycle.inject.service.impl.ServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(value = {"cn.hutool.extra.spring","com.sjl.bean.life.cycle"})
@Import(SpringUtil.class)
public class BeanLifeCycleApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext run =
        SpringApplication.run(BeanLifeCycleApplication.class, args);
//    run.getBean(ServiceImpl.class).doSave();
//    run.close();
  }
}

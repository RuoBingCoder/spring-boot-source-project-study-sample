package com.sjl.boot.autowired.inject.sample;

import com.sjl.boot.autowired.inject.sample.annotation.EnableCustomAsync;
import com.sjl.boot.autowired.inject.sample.constant.Proxy;
import com.sjl.boot.autowired.inject.sample.controller.TestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@SpringBootApplication
@EnableCustomAsync(Proxy.CGLIB)
public class BootAutowiredInjectSampleApplication implements CommandLineRunner {

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(BootAutowiredInjectSampleApplication.class, args);
    run.getEnvironment().getProperty("");
  }

  @Override
  public void run(String... args) throws Exception {
    Field myAsync = ReflectionUtils.findField(TestController.class, "myAsync");
    assert myAsync != null;
    System.out.println("======>"+myAsync.getName());
  }
}

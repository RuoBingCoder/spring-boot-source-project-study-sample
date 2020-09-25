package com.sjl.boot.autowired.inject.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootAutowiredInjectSampleApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(BootAutowiredInjectSampleApplication.class, args);
    run.getEnvironment().getProperty("");
  }
}

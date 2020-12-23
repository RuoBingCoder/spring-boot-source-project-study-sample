package com.sjl.custom.spring.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:sjl.xml")
public class CustomSpringXmlSpringBootSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomSpringXmlSpringBootSampleApplication.class, args);
  }
}

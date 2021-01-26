package com.github.custom.spring.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:simple-bean.xml")
public class CustomSpringXmlSpringBootSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomSpringXmlSpringBootSampleApplication.class, args);
  }
}

package com.github.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自定义引导starter应用程序
 *
 * @author shijianlei
 * @date 2021-01-26 13:50:13
 */
@SpringBootApplication
@Deprecated
public class CustomSpringBootStarterApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomSpringBootStarterApplication.class, args);
  }
}

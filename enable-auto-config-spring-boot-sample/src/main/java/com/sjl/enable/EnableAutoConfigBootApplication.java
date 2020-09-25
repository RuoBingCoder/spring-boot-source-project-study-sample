package com.sjl.enable;

import com.sjl.enable.annotation.SjlScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SjlScanner(basePackages = "com.sjl.enable.service")
@EnableScheduling
public class EnableAutoConfigBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnableAutoConfigBootApplication.class, args);
    }

}

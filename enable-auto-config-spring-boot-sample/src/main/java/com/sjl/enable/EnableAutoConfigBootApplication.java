package com.sjl.enable;

import com.sjl.enable.annotation.SjlScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SjlScanner(basePackages = "com.sjl.enable.service")
public class EnableAutoConfigBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnableAutoConfigBootApplication.class, args);
    }

}

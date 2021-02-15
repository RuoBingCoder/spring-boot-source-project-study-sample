package com.github.nacos.sample;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "spring-boot-nacos-sample",autoRefreshed = true)
public class SpringBootNacosSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNacosSampleApplication.class, args);
    }

}

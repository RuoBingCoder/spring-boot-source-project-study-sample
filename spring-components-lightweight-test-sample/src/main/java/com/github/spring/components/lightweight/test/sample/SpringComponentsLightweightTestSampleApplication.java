package com.github.spring.components.lightweight.test.sample;

import com.github.spring.components.lightweight.test.sample.service.ServiceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringComponentsLightweightTestSampleApplication implements CommandLineRunner {

    @Autowired
    private ServiceBean serviceBean;
    public static void main(String[] args) {
        SpringApplication.run(SpringComponentsLightweightTestSampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //test dubbo service bean inject ref
        log.info("service bean ref: {}",serviceBean.refName());
    }
}

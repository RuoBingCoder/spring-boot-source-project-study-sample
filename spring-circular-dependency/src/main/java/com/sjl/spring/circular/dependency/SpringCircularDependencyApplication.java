package com.sjl.spring.circular.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCircularDependencyApplication implements CommandLineRunner {
    @Autowired
    private DefaultListableBeanFactory factory;

    public static void main(String[] args) {
        SpringApplication.run(SpringCircularDependencyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        factory.getBean("aopController");
    }


}
package com.github.spring.dependency.inject.demo;

import com.github.spring.dependency.inject.demo.annotation.EnableCustomAsync;
import com.github.spring.dependency.inject.demo.constant.Proxy;
import com.github.spring.dependency.inject.demo.controller.TestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@SpringBootApplication
@EnableCustomAsync(Proxy.CGLIB)
@EnableAsync
public class SpringDependencyInjectSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringDependencyInjectSampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Field myAsync = ReflectionUtils.findField(TestController.class, "myAsync");
        assert myAsync != null;
        System.out.println("======>"+myAsync.getName());
        Thread.currentThread().getContextClassLoader();

    }
}

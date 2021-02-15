package com.github.custom.aop.controller;

import com.github.custom.aop.annotation.MyAutowired;
import com.github.custom.aop.service.CglibService;
import com.github.custom.aop.service.FileService;
import com.github.custom.aop.service.HelloService;
import com.github.custom.aop.utils.SpringBeanUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author: JianLei
 * @date: 2020/9/22 7:26 下午
 * @description:
 */
@RestController
@RequestMapping
public class AopController {

    @MyAutowired
    private HelloService helloService;

    @MyAutowired
    private FileService fileService;

    @GetMapping("/hello")
    public String hello() {
        //    helloService.sayHello("这是测试");
        System.out.println("======================");
        String list = helloService.list(1, 10, "张三");
        // 测试cglib代理
        CglibService cglibService = SpringBeanUtil.getContext().getBean(CglibService.class);
        cglibService.selectOrderById(10001);
        return "success";
    }


    @GetMapping("/file")
    public String file() {
        fileService.readFile(new File("/Users/shijianlei/IdeaProjects/spring-boot-project-example/custom-aop-spring-boot-sample/src/main/java/com/sjl/custom/aop/controller/AopController.java"));
        return "success()";
    }

}

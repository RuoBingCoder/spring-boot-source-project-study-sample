package com.github.spring.components.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jianlei.shi
 * @date 2021/1/29 2:33 下午
 * @description TestController
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {


    /**
     * 出口
     *
     * @param name     的名字
     * @param response 响应
     * @return
     * @author jianlei.shi
     * @date 2021-01-29 16:10:39
     *  // @Aspect 注解 在拦截方法要过滤掉HttpServletResponse
     */
    @PostMapping("/export")
    public  void export(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        System.out.println("iiiiiiiiiiiiiiiii");
        log.info("export param is:{}",name);
//        response.getOutputStream().write("export success".getBytes());
    }
}

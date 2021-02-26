package com.github.spring.components.learning.controller;

import com.github.spring.components.learning.lighthttp.service.LightHttpOperateService;
import com.github.spring.components.learning.lighthttp.service.LightHttpQueryService;
import com.github.spring.components.learning.params.OrderRequestParam;
import common.request.CRequestParam;
import http.ModelResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private LightHttpQueryService queryService;
    @Autowired
    private LightHttpOperateService operateService;

    /**
     * 出口
     *
     * @param name     的名字
     * @param response 响应
     * @return
     * @author jianlei.shi
     * @date 2021-01-29 16:10:39
     * // @Aspect 注解 在拦截方法要过滤掉HttpServletResponse
     */
    @PostMapping("/export")
    public void export(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        System.out.println("iiiiiiiiiiiiiiiii");
        log.info("export param is:{}", name);
//        response.getOutputStream().write("export success".getBytes());
    }

    @GetMapping("/lightGet")
    @ResponseBody
    public ModelResult<String> lightGet() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("WeChat");
        request.setAddress("Beijing");
        request.setId(1000L);
        final String res = queryService.queryList(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/insert")
    @ResponseBody
    public ModelResult<String> insert() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("QQ");
        request.setAddress("ShangHai");
        request.setId(2000L);
        final String res = queryService.insert(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/lightTwo")
    @ResponseBody
    public ModelResult<String> lightTwo() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("WeChat");
        request.setAddress("Beijing");
        request.setId(1000L);
        final String res = operateService.queryList(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/insertTwo")
    @ResponseBody
    public ModelResult<String> insertTwo() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("QQ");
        request.setAddress("ShangHai");
        request.setId(2000L);
        final String res = operateService.insert(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/queryOrder")
    @ResponseBody
    public ModelResult<String> queryOrder() {
        ModelResult<String> result = new ModelResult<String>();
        OrderRequestParam orderRequestParam = new OrderRequestParam();
        orderRequestParam.setOrderId(10001L);
        orderRequestParam.setOrderName("吹风机");
        orderRequestParam.setCount(100);
        orderRequestParam.setReceiver("张三");
        orderRequestParam.setAddress("北京市海淀区上地南路");
        final ModelResult<String> modelResult = operateService.queryOrder(orderRequestParam);
        return modelResult;
    }
}

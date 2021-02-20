package com.github.spring.components.lightweight.test.sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.spring.components.lightweight.test.sample.job.Task;
import com.github.spring.components.lightweight.test.sample.job.TaskWrapper;
import com.github.spring.components.lightweight.test.sample.pojo.Order;
import com.github.spring.components.lightweight.test.sample.service.BizService;
import common.request.CRequestParam;
import http.ModelResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private TaskWrapper tw;


    @Autowired
    private BizService bizService;

    @PostMapping("/export")
    public void export(@RequestParam("name") String name, HttpServletResponse resp) throws IOException {
        try {
            for (int i = 0; i < 1000; i++) {
                System.out.println("count: " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("export param is:{}", name);
//        response.getOutputStream().write("export success".getBytes());
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Map<String, String> map = new HashMap<>();
        map.put("status", "success");
        writer.write(map.toString());
    }

    @PostMapping("/order")
    @ResponseBody
    public ModelResult<String> order(@RequestBody Order order) throws NoSuchAlgorithmException {
        ModelResult<String> result = new ModelResult<>();
        if (order == null) {
            ModelResult.error("order is null");
        }
        Task task = new Task();
        Random random = SecureRandom.getInstanceStrong();
        task.setId(random.nextInt());
        task.setOrder(order);
        if (tw.offer(task)) {
            result.setCode(200);
            result.setMsg("success");
            return result;
        }
        return ModelResult.error("添加订单失败");
    }

    @GetMapping("/getAppName")
    @ResponseBody
    public ModelResult<String> getAppName(@RequestParam String param) {
//        log.info("开始获取appName");
        final ModelResult<String> result = bizService.getAppName();
//        log.info("获取appName结束");
        return result;
    }

    @PostMapping("/map")
    @ResponseBody
    public ModelResult<String> map(@RequestBody CRequestParam params) {
        log.info("map params is:{}", JSONObject.toJSONString(params == null ? "hello" : params));
        ModelResult<String> result = new ModelResult<>();
        assert params != null;
        result.setData(params.toString());
        return result;
    }
}

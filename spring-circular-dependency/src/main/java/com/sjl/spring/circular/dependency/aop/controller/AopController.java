package com.sjl.spring.circular.dependency.aop.controller;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.circular.dependency.pojo.A;
import com.sjl.spring.circular.dependency.pojo.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/11/26 下午2:54
 * @description: AopController
 */
@RequestMapping("/aop")
@RestController
public class AopController {

    @Autowired
    private A a;

    @PostMapping(value = "/log", name = "true")
    public String log(@RequestBody  LogModel logModel) {
        a.output();
        return "key->" + JSONObject.toJSONString(logModel);
    }
}

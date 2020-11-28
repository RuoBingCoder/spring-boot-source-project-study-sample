package com.sjl.spring.circular.dependency.aop.controller;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.circular.dependency.pojo.LogModel;
import org.springframework.web.bind.annotation.*;

/**
 * @author: JianLei
 * @date: 2020/11/26 下午2:54
 * @description: AopController
 */
@RequestMapping("/aop")
@RestController
public class AopController {

    @PostMapping(value = "/log", name = "true")
    public String log(@RequestBody  LogModel logModel) {
        return "key->" + JSONObject.toJSONString(logModel);
    }
}

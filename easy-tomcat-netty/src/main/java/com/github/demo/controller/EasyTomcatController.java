package com.github.demo.controller;

import com.github.tomcat.core.annotation.EasyController;
import com.github.tomcat.core.annotation.EasyPostMapping;
import com.github.tomcat.core.annotation.EasyRequestMapping;
import com.github.tomcat.core.annotation.EasyRequestParams;
import com.github.tomcat.http.EasyHttpResponse;

/**
 * @author: JianLei
 * @date: 2020/10/8 12:54 下午
 * @description: EasyTomcatController
 */
@EasyController
@EasyRequestMapping("/easy")
public class EasyTomcatController {

    @EasyPostMapping("/sayHello")
    public EasyHttpResponse sayHello(@EasyRequestParams("name") String name){
        return EasyHttpResponse.success("这是一个简单的由netty实现的tomcat并实现了ioc和mvc"+name);

    }
    @EasyPostMapping("/getUserName")
    public EasyHttpResponse getUserName(@EasyRequestParams("id") Integer id){
        return EasyHttpResponse.success("从数据库获取用户名为:"+id+"李四");

    }
}

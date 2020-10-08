package com.sjl.demo.controller;

import com.sjl.tomcat.core.annotation.EasyController;
import com.sjl.tomcat.core.annotation.EasyPostMapping;
import com.sjl.tomcat.core.annotation.EasyRequestMapping;
import com.sjl.tomcat.core.annotation.EasyRequestParams;
import com.sjl.tomcat.http.EasyHttpResponse;

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

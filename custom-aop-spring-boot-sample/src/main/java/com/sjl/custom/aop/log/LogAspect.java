package com.sjl.custom.aop.log;

import com.alibaba.fastjson.JSONObject;
import com.sjl.custom.aop.annotation.After;
import com.sjl.custom.aop.annotation.Aspect;
import com.sjl.custom.aop.annotation.Before;
import com.sjl.custom.aop.annotation.Pointcut;
import com.sjl.custom.aop.aspect.JoinPoint;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/22 5:32 下午
 * @description: aop日志打印
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("public .* com.sjl.custom.aop.service..*Service..*(.*)")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void before(JoinPoint joinpoint){
        log.info("=====>method 入参:{}", JSONObject.toJSONString(joinpoint.getArguments()));
        log.info("=====>method class:{}",joinpoint.getThis().toString());
        log.info("=====>method name:{}",joinpoint.getMethod().getName());

    }

    @After(value = "pointcut()")
    public void After(Object result){
        log.info("-------->返回值是:{}",result);

    }

}

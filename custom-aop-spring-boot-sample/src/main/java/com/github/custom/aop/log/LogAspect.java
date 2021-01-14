package com.github.custom.aop.log;

import com.alibaba.fastjson.JSONObject;
import com.github.custom.aop.annotation.After;
import com.github.custom.aop.annotation.Aspect;
import com.github.custom.aop.annotation.Before;
import com.github.custom.aop.annotation.Pointcut;
import com.github.custom.aop.aspect.JoinPoint;
import lombok.extern.slf4j.Slf4j;
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
    /**
     * @see  org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor
     */
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

package com.sjl.custom.aop.aspect;

import com.sjl.custom.aop.annotation.Aspect;
import com.sjl.custom.aop.annotation.Before;
import com.sjl.custom.aop.annotation.Pointcut;
import com.sjl.custom.aop.bean.JoinPoint;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/22 5:32 下午
 * @description:
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("com.sjl.custom.aop.service")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void before(JoinPoint joinpoint){
        log.info("=====>method 入参:{}",joinpoint.getParams());
        log.info("=====>method class:{}",joinpoint.getClazz().toString());
        log.info("=====>method name:{}",joinpoint.getMethodName());

    }

}

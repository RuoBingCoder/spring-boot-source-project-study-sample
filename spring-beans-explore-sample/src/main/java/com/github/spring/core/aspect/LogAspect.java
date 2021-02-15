package com.github.spring.core.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/21 10:03 上午
 * @description:
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.github.spring.core.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("=======>请求入参:{}", JSONObject.toJSONString(args));
    }
}

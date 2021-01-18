package com.github.spring.circular.dependency.aop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/26 下午2:50
 * @description: AdviceConfig
 * {@link AbstractBeanFactory #createBean}
 * {@link AbstractAutowireCapableBeanFactory #resolveBeforeInstantiation} 解析Aspect注解
 */
@Aspect
@Component
@Slf4j
public class AdviceConfig {

    @Pointcut("execution(public * com.github.spring.circular.dependency.circular.*.*(..))")
    public void pointcut(){

    }
    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint){
        log.info("===>args is:{}",joinPoint.getArgs());

    }
}

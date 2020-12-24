package com.sjl.spring.components.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectJAdvisorsBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jianlei
 * @date: 2020/4/8
 * @description: aop切面做全局日志处理
 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(Object, String)
 * @see org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator#shouldSkip(Class, String)
 * @see BeanFactoryAspectJAdvisorsBuilder#buildAspectJAdvisors()
 * @see org.springframework.beans.factory.BeanFactoryUtils#beanNamesForTypeIncludingAncestors 获取所有beanName
 * @see org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory#isAspect(Class)  获取Aspect beanName 添加打破List
 *
 * CglibAopProxy#getCallbacks 获取拦截器
 */
@Aspect
@Configuration
@Slf4j
public class LogAspect {
    /**
     * execution(* com.savage.server..*.*(..))
     */

    @Pointcut("execution(* com.sjl.spring.components.controller..*.*(..))")
    public void controllerCut() {
    }

    @Before("controllerCut()")
    private void doBefore(JoinPoint jp) {
        String methodName = jp.getSignature().getName() + "()";
        String methodParams =JSONObject.toJSONString(jp.getArgs());
        log.info("方法名 ==>>>>:{}", methodName);
        log.info("方法入参是 ==>>>> :{}", methodParams);
    }

    @AfterReturning(returning = "obj",pointcut = "controllerCut()")
    private Object doAfter(JoinPoint point, Object obj) {
        log.info(">>>>>返回值是:{}", JSON.toJSONString(obj));
        return obj;
    }

}

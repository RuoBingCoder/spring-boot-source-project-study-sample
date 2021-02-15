package com.github.spring.components.learning.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectJAdvisorsBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jianlei
 * @date: 2020/4/8
 * @description: aop切面做全局日志处理
 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(Object, String)
 * @see org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator#shouldSkip(Class, String)
 * @see BeanFactoryAspectJAdvisorsBuilder#buildAspectJAdvisors()
 * @see org.springframework.beans.factory.BeanFactoryUtils#beanNamesForTypeIncludingAncestors 获取所有beanName
 * @see org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory#isAspect(Class)  获取Aspect beanName 添加打破List
 * <p>
 * CglibAopProxy#getCallbacks 获取拦截器
 *
 * 流程的话分为 1、shouldSkip()->2、findCandidateAdvisors 找到Advise ,没有实现Advise接口
 * 2.1、创建Advise {@link org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory#getAdvisors)} 添加到advisors 集合中
 * {@link AnnotationAwareAspectJAutoProxyCreator#findCandidateAdvisors()}
 * -> 3.返回 false｜true
 * </p>
 *
 *  @see ConfigurationClassPostProcessor#enhanceConfigurationClasses 加了此注解 {@link Configuration} 会被代理
 */
@Aspect
@Slf4j
@Component
public class LogAspect {
    /**
     * execution(* com.savage.server..*.*(..))
     */

    @Pointcut("execution(* com.github.spring.components.learning.controller..*.*(..))")
    public void controllerCut() {
    }

    @Before("controllerCut()")
    private void doBefore(JoinPoint jp) {
        String methodName = jp.getSignature().getName() + "()";
        String methodParams = JSONObject.toJSONString(jp.getArgs());
        log.info("方法名 ==>>>>:{}", methodName);
        log.info("方法入参是 ==>>>> :{}", methodParams);
    }

    @Around("controllerCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("===>  around get params:{}", pjp.getArgs());
        final Object[] args = pjp.getArgs();
        final List<Object> argsList = Arrays.stream(args).filter(t -> (!(t instanceof HttpServletResponse))).filter(t -> !(t instanceof HttpServletRequest)).collect(Collectors.toList());
        //进行json转换的的时候 JSONObject.toJSONString(argsList);
        return pjp.proceed(args);
//        return pjp.proceed();

    }

    @AfterReturning(returning = "obj", pointcut = "controllerCut()")
    private Object doAfter(JoinPoint point, Object obj) {
        log.info(">>>>>返回值是:{}", JSON.toJSONString(obj));
        return obj;
    }

}

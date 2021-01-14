package com.github.custom.aop.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.github.custom.aop.exception.AopException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/29 10:53 上午
 * @description: CglibMethodInterceptor
 */
@Slf4j
public class CglibMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, objects);
        }
        String methodName = method.getName();
        //打印日志
        log.info("CGLIB Proxy 前置通知拦截方法名是:{} 参数是:{}",methodName, JSONObject.toJSONString(objects));
        Object result = null;
        try{
            //前置通知
            result = methodProxy.invokeSuper(o, objects);
            //返回通知, 可以访问到方法的返回值、
            log.info("CGLIB Proxy 后置通知拦截方法返回值是:{}",JSONObject.toJSONString(result));
        } catch (Exception e){
            log.error("CGLIB Proxy 方法调用出现异常",e);
            //异常通知, 可以访问到方法出现的异常
            throw new AopException("CGLIB Proxy 方法调用出现异常");
        }
        //后置通知. 因为方法可以能会出异常, 所以访问不到方法的返回值
        //打印日志
        return result;
    }


}


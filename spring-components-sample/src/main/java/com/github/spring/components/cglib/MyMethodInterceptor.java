package com.github.spring.components.cglib;

import com.alibaba.fastjson.JSONObject;
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
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        String methodName = method.getName();
        //打印日志
        System.out.printf("CGLIB Proxy 前置通知拦截方法名是:%s 参数是:%s",methodName, JSONObject.toJSONString(args));
        System.out.println("  ");
        Object result = null;
        try{
            //前置通知
            result = methodProxy.invokeSuper(target, args);
            //返回通知, 可以访问到方法的返回值、
            System.out.printf("CGLIB Proxy 后置通知拦截方法返回值是:{%s}",JSONObject.toJSONString(result));
            System.out.println("  ");

        } catch (Exception e){
            //异常通知, 可以访问到方法出现的异常
            throw new RuntimeException("CGLIB Proxy 方法调用出现异常");
        }
        //后置通知. 因为方法可以能会出异常, 所以访问不到方法的返回值
        //打印日志
        return result;
    }


}


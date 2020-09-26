package com.sjl.custom.aop.interceptor;

import com.sjl.custom.aop.aspect.AbstractAspectAdvice;
import com.sjl.custom.aop.aspect.Advice;
import com.sjl.custom.aop.aspect.JoinPoint;
import com.sjl.custom.aop.aspect.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/25 5:55 下午
 * @description:
 */
public class MethodBeforeAdviceInterceptor extends AbstractAspectAdvice implements Advice,MethodInterceptor {
    private JoinPoint joinPoint;
    public MethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before() throws Throwable{
        //传送了给织入参数
        super.invokeAdviceMethod(this.joinPoint,null,null);

    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = mi;
        before();
        return mi.proceed();
    }
}

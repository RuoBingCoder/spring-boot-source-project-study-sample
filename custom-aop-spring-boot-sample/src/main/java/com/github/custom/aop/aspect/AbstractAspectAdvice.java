package com.github.custom.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/25 5:53 下午
 * @description:
 */

public abstract class AbstractAspectAdvice implements Advice {
    private final Method aspectMethod;
    private final Object aspectTarget;
    protected AbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    /**
     * 给增强方法参数赋值
     * @param joinPoint
     * @param returnValue
     * @param tx
     * @throws Throwable
     */
    public void invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable{
        Class<?> [] paramTypes = this.aspectMethod.getParameterTypes();
        if(paramTypes.length == 0){
            this.aspectMethod.invoke(aspectTarget);
        }else{
            Object [] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i ++) {
                if(paramTypes[i] == JoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            this.aspectMethod.invoke(aspectTarget, args);
        }
    }

}

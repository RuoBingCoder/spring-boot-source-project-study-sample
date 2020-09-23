package com.sjl.custom.aop.proxy;

import com.alibaba.fastjson.JSONObject;
import com.sjl.custom.aop.aspect.LogAspect;
import com.sjl.custom.aop.bean.AopBeanDefinition;
import com.sjl.custom.aop.bean.AspectHolder;
import com.sjl.custom.aop.bean.JoinPoint;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:30 下午
 * @description:
 */
public class JdkProxy implements InvocationHandler {

  private AspectHolder<AopBeanDefinition> holder;

  public JdkProxy(AspectHolder<AopBeanDefinition> holder) {
    this.holder = holder;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //调用before方法
    handleBefore(holder, method, args);
    return method.invoke(holder.getConfigAttribute().get(0).getTarget(), args);
  }

  private void handleBefore(AspectHolder<AopBeanDefinition> holder, Method method, Object[] args)
          throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    for (AopBeanDefinition abd : holder.getConfigAttribute()) {
      if (abd.getBeforeMethodName()==null||"".equals(abd.getBeforeMethodName())){
        continue;
      }
      Method method1 =
          holder.getClazz().getMethod(abd.getBeforeMethodName(),JoinPoint.class);
      Class<?>[] parameterTypes = method1.getParameterTypes();
      Object[] aspectArgs = new Object[parameterTypes.length];
      for (int i = 0; i < parameterTypes.length; i++) {
        if (parameterTypes[i].equals(JoinPoint.class)) {
          JoinPoint joinPoint = new JoinPoint();
          joinPoint.setClazz(method.getDeclaringClass());
          joinPoint.setMethodName(method.getName());
          joinPoint.setParams(JSONObject.toJSONString(args));
          aspectArgs[i] = joinPoint;
          method1.invoke(holder.getConfigAttribute().get(0).getAspectTarget(), aspectArgs);
        }
      }
    }
  }

}

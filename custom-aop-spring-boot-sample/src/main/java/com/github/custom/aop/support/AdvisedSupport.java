package com.github.custom.aop.support;

import com.github.custom.aop.bean.AopConfig;
import com.github.custom.aop.interceptor.MethodAfterReturnAdviceInterceptor;
import com.github.custom.aop.interceptor.MethodBeforeAdviceInterceptor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: JianLei
 * @date: 2020/9/25 6:03 下午
 * @description:
 */
@Data
public class AdvisedSupport {
  private Class<?> targetClass;

  private Object target;

  private AopConfig config;

  private Pattern pointCutClassPattern;

  private transient Map<Method, List<Object>> methodCache;

  public AdvisedSupport(AopConfig config) {
    this.config = config;
  }

  /**
   * 获取拦截器
   * @param method
   * @param targetClass
   * @return
   * @throws Exception
   */
  public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception{
    List<Object> cached = methodCache.get(method);
    if(cached == null){
      Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());

      cached = methodCache.get(m);

      //底层逻辑，对代理方法进行一个兼容处理
      this.methodCache.put(m,cached);
    }

    return cached;
  }
  public void parse() throws IllegalAccessException, InstantiationException {
    String pointCut =
        config
            .getPointCut()
            .replaceAll("\\.", "\\\\.")
            .replaceAll("\\\\.\\*", ".*")
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");
    Pattern pattern = Pattern.compile(pointCut);
    methodCache = new HashMap<>();
    Class<?> aspectClass = config.getAspectClass();
    Map<String, Method> aspectMethodCache = new HashMap<>();
    // 存放增强类方法用于后期拦截器
    for (Method method : aspectClass.getMethods()) {
      aspectMethodCache.put(method.getName(), method);
    }
    for (Method method : targetClass.getMethods()) {
      String methodString = method.toString();
      if (methodString.contains("throws")) {
        methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
      }
      Matcher matcher = pattern.matcher(methodString);
      if (matcher.matches()) {
        List<Object> advices = new LinkedList<>();
        // 把每一个方法包装成 Method Interceptor
        // before
        if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
          // 创建一个Advice
          advices.add(
              new MethodBeforeAdviceInterceptor(
                  aspectMethodCache.get(config.getAspectBefore()), aspectClass.newInstance()));
        }
        // after
        if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
          // 创建一个Advice
          // advices.add(new
          // After(aspectMethodCache.get(config.getAspectAfter()),aspectClass.newInstance()));
          advices.add(new MethodAfterReturnAdviceInterceptor(aspectMethodCache.get(config.getAspectAfter()),aspectClass.newInstance()));
        }
        methodCache.put(method, advices);
      }
    }
  }
}

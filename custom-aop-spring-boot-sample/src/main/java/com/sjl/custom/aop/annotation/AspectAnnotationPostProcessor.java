package com.sjl.custom.aop.annotation;

import com.sjl.custom.aop.definition.AopBeanDefinition;
import com.sjl.custom.aop.bean.AopConfig;
import com.sjl.custom.aop.factory.ProxyFactory;
import com.sjl.custom.aop.support.AdvisedSupport;
import com.sjl.custom.aop.utils.SpringBeanUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:14 下午
 * @description: 1、
 */
@Component
@Slf4j
@DependsOn("springBeanUtil")
public class AspectAnnotationPostProcessor implements BeanPostProcessor {

  private static final List<AopBeanDefinition> AOP_BEAN_DEFINITIONS = new ArrayList<>();

  @SneakyThrows
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    parseAspectBean(bean);
    if (!CollectionUtils.isEmpty(AOP_BEAN_DEFINITIONS)) {
      matchProxyClassAndCreateProxy();
    }
    return bean;
  }

  /**
   * 匹配代理方法,并添加拦截器
   *
   * @date 2020/9/25 pm 6:21
   */
  private void matchProxyClassAndCreateProxy()
      throws IllegalAccessException, InstantiationException {
    for (AopBeanDefinition definition : AspectAnnotationPostProcessor.AOP_BEAN_DEFINITIONS) {
      for (Object serviceBean : SpringBeanUtil.getServicesBeans()) {
        if (serviceBean.getClass().getInterfaces().length > 0) {
          AdvisedSupport advisedSupport = instantiationAopConfig(definition, serviceBean);
          createProxy(advisedSupport);
        }
      }
    }
  }

  private AdvisedSupport instantiationAopConfig(AopBeanDefinition definition, Object bean)
      throws InstantiationException, IllegalAccessException {
    AopConfig config = new AopConfig();
    config.setPointCut(definition.getPointcut());
    config.setAspectClass(definition.getAspectClass());
    config.setAspectBefore(definition.getBeforeMethodName());
    config.setAspectAfter(definition.getAfterMethodName());
    AdvisedSupport support = new AdvisedSupport(config);
    support.setTarget(bean);
    support.setTargetClass(bean.getClass().getInterfaces()[0]);
    support.parse();
    return support;
  }

  /**
   * 创建代理
   *
   * @param advisedSupport
   */
  private void createProxy(AdvisedSupport advisedSupport) throws IllegalAccessException {

    for (Object obj : SpringBeanUtil.getInjectBeans()) {
      for (Field field : obj.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        if (field.getType().equals(advisedSupport.getTargetClass())) {
          field.set(obj, ProxyFactory.getJDKProxy(advisedSupport));
        }
      }
    }
  }

  /**
   * 解析含有@Aspect bean属性
   *
   * @param bean
   */
  private void parseAspectBean(Object bean) {
    synchronized (AOP_BEAN_DEFINITIONS) {
      if (bean.getClass().isAnnotationPresent(Aspect.class)) {
        AopBeanDefinition aopBean = new AopBeanDefinition();
        for (Method method : bean.getClass().getDeclaredMethods()) {
          aopBean.setAspectClass(bean.getClass());
          aopBean.setAspectTarget(bean);
          if (method.isAnnotationPresent(Pointcut.class)) {
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            String aopPackage = pointcut.value();
            aopBean.setPointcut(aopPackage);
          } else if (method.isAnnotationPresent(Before.class)) {
            Before before = method.getAnnotation(Before.class);
            aopBean.setBefore(before.value());
            aopBean.setBeforeMethodName(method.getName());
          } else if (method.isAnnotationPresent(After.class)) {
            After after = method.getAnnotation(After.class);
            aopBean.setAfter(after.value());
            aopBean.setAfterMethodName(method.getName());
          }
        }
        AOP_BEAN_DEFINITIONS.add(aopBean);
      }
    }
  }
}

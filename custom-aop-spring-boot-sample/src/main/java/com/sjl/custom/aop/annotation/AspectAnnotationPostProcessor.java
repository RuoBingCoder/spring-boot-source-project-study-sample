package com.sjl.custom.aop.annotation;

import com.sjl.custom.aop.bean.AopBeanDefinition;
import com.sjl.custom.aop.bean.AspectHolder;
import com.sjl.custom.aop.exception.AopException;
import com.sjl.custom.aop.factory.ProxyFactory;
import com.sjl.custom.aop.service.HelloService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/9/22 3:14 下午
 * @description:
 * 1、
 */
@Component
@Slf4j
public class AspectAnnotationPostProcessor implements BeanPostProcessor, DisposableBean {

  private static final Map<Class<?>, List<AopBeanDefinition>> CACHE_ASPECT_BEAN_DEFINITION = new HashMap<>();

  public static final Set<Object> PROXY_CLASS_SET=new LinkedHashSet<>();
  @SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    // 获取controller字段
    parseMyAutowired(bean);
    //解析增强类和增强方法
    List<AopBeanDefinition> aopBeanDefinitions = parseAspectBean(bean);
    if (CollectionUtils.isEmpty(aopBeanDefinitions) && !CACHE_ASPECT_BEAN_DEFINITION.isEmpty()) {
      boolean isNeedProxyBean = isNeedProxyBean(bean);
      if (isNeedProxyBean) {
        createProxy();
      }
    }
    return bean;
  }

  private void parseMyAutowired(Object bean) {
    for (Field field : bean.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(MyAutowired.class)){
          PROXY_CLASS_SET.add(bean);
      }
    }
  }

  /**
   * @param bean
   * @return
   */
  private boolean isNeedProxyBean(
          Object bean) {
    checkPointcut();
    for (Map.Entry<Class<?>, List<AopBeanDefinition>> classListEntry :
        AspectAnnotationPostProcessor.CACHE_ASPECT_BEAN_DEFINITION.entrySet()) {
      // 简单处理
      for (AopBeanDefinition b : classListEntry.getValue()) {
        int i = bean.getClass().getName().lastIndexOf(".");

        String expressionPackagePrefix = b.getExpression();
        if (expressionPackagePrefix == null) {
          continue;
        }
        log.info("expressionPackagePrefix is:{}", expressionPackagePrefix);
        String classNamePrefix = bean.getClass().getName().substring(0, i);
        int i1 = classNamePrefix.lastIndexOf(".");
        // 主要用于做接口代理,需要判断接口名
        String secondClassNamePrefix = bean.getClass().getName().substring(0, i1);
        log.info("secondClassNamePrefix is:{}", secondClassNamePrefix);
        // 判断bean是否是接口
        if (expressionPackagePrefix.equals(secondClassNamePrefix)
            && bean.getClass().getInterfaces().length > 0) {
          b.setTargetClass(bean.getClass().getInterfaces()[0]);
          b.setTarget(bean);
          return true;
        }
      }
    }
    return false;
  }

  private void checkPointcut() {
    for (Map.Entry<Class<?>, List<AopBeanDefinition>> classListEntry :
        AspectAnnotationPostProcessor.CACHE_ASPECT_BEAN_DEFINITION.entrySet()) {
      classListEntry
          .getValue()
          .forEach(
              a -> {
                if (("".equals(a.getExpression()) && !"".equals(a.getBefore()))
                    || ("".equals(a.getExpression()) && !"".equals(a.getAfter()))) {
                  throw new AopException(
                      "@Pointcut value is null and @Before value is not null || @After value is not null");
                }
              });
    }
  }
  /**
   * 创建代理
   *
   */
  private void createProxy() throws IllegalAccessException {
    for (Map.Entry<Class<?>, List<AopBeanDefinition>> classListEntry :
        AspectAnnotationPostProcessor.CACHE_ASPECT_BEAN_DEFINITION.entrySet()) {
      AspectHolder<AopBeanDefinition> holder = new AspectHolder<>();
      holder.setClazz(classListEntry.getKey());
      holder.setConfigAttribute(classListEntry.getValue());
      for (Object obj : PROXY_CLASS_SET) {
        for (Field field : obj.getClass().getDeclaredFields()) {
          field.setAccessible(true);
          for (AopBeanDefinition aopBeanDefinition : holder.getConfigAttribute()) {
            if (field.getType().equals(aopBeanDefinition.getTargetClass())) {
              field.set(obj, new ProxyFactory(holder).crateProxy());
            }
          }
        }
      }
      }
  }

  /**
   * 解析含有@Aspect bean属性
   *
   * @param bean
   */
  private List<AopBeanDefinition> parseAspectBean(Object bean) {
    List<AopBeanDefinition> aopBeanDefinitions = new LinkedList<>();
    if (bean.getClass().isAnnotationPresent(Aspect.class)) {
      for (Method method : bean.getClass().getDeclaredMethods()) {
        AopBeanDefinition aopBean = new AopBeanDefinition();
        aopBean.setAspectClass(bean.getClass());
        aopBean.setAspectTarget(bean);
        if (method.isAnnotationPresent(Pointcut.class)) {
          Pointcut pointcut = method.getAnnotation(Pointcut.class);
          String aopPackage = pointcut.value();
          aopBean.setExpression(aopPackage);
        } else if (method.isAnnotationPresent(Before.class)) {
          Before before = method.getAnnotation(Before.class);
          aopBean.setBefore(before.value());
          aopBean.setBeforeMethodName(method.getName());
        } else if (method.isAnnotationPresent(After.class)) {
          After after = method.getAnnotation(After.class);
          aopBean.setAfter(after.value());
          aopBean.setAfterMethodName(method.getName());
        }
        aopBeanDefinitions.add(aopBean);
      }
      CACHE_ASPECT_BEAN_DEFINITION.put(bean.getClass(), aopBeanDefinitions);
    }
    return aopBeanDefinitions;
  }

  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    Class<HelloService> helloServiceClass = HelloService.class;
    System.out.println(helloServiceClass.newInstance());
  }

  @Override
  public void destroy() throws Exception {
    PROXY_CLASS_SET.clear();
    CACHE_ASPECT_BEAN_DEFINITION.clear();
  }
}

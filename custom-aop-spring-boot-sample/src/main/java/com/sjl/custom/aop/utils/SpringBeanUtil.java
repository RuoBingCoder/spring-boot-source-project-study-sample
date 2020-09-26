package com.sjl.custom.aop.utils;

import com.sjl.custom.aop.annotation.MyAutowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/9/25 9:40 下午
 * @description:
 */
@Component
@Slf4j
@Order(-12000)
public class SpringBeanUtil
    implements ApplicationContextAware, InitializingBean, EnvironmentAware, DisposableBean {
  private static ApplicationContext applicationContext;
  private static final Set<Object> AUTOWIRED_BEANS_SET = new LinkedHashSet<>();
  private static final Set<Object> SERVICE_BEANS_SET = new LinkedHashSet<>();
  private static final String SERVICE_NAME_SPACE = "service.namespace";
  private static final String MATCH_CONTROLLER = "match.controller";
  private Environment environment;

  public static Set<Object> getInjectBeans() {
    return AUTOWIRED_BEANS_SET;
  }

  public static Set<Object> getServicesBeans() {
    return SERVICE_BEANS_SET;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringBeanUtil.applicationContext = applicationContext;
  }

  public static ApplicationContext getContext() {
    return applicationContext;
  }

  private void getMyAutowiredBeans() {
    for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
      try {
        Object bean = null;
        if (beanDefinitionName.startsWith(
            Objects.requireNonNull(environment.getProperty(SERVICE_NAME_SPACE)))) {
          bean = applicationContext.getBean(beanDefinitionName);
          SERVICE_BEANS_SET.add(bean);
        }
        if (AUTOWIRED_BEANS_SET.contains(bean)) {
          continue;
        }
        Arrays.stream(applicationContext.getBeanDefinitionNames())
            .filter(
                s -> s.endsWith(Objects.requireNonNull(environment.getProperty(MATCH_CONTROLLER))))
            .forEach(
                s -> {
                  Object bean1 = applicationContext.getBean(s);
                  for (Field field : bean1.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(MyAutowired.class)) {
                      AUTOWIRED_BEANS_SET.add(bean1);
                    }
                  }
                });

      } catch (Exception e) {
        log.error("getMyAutowiredBeansSet error:", e);
        throw new RuntimeException("getMyAutowiredBeansSet error");
      }
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("-----MyAutowired bean set 初始化完毕!--------");
    getMyAutowiredBeans();
  }

  @Override
  public void destroy() throws Exception {
    log.info("=============>开始清空缓存<==============");
    AUTOWIRED_BEANS_SET.clear();
    SERVICE_BEANS_SET.clear();
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}

package com.sjl.custom.spring.xml.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/9/10 4:48 下午
 * @description:
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext = null;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringBeanUtil.applicationContext = applicationContext;
  }

  public static <T> T getBeanByType(Class<?> clazz) throws Exception {
    if (clazz == null) {
      throw new Exception("class is not null!");
    }
    Map<String, ?> beansOfType = applicationContext.getBeansOfType(clazz);

    beansOfType.forEach((key, value) -> System.out.println(key + "--" + value));
    return (T) beansOfType;
  }
}

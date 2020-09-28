package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:45 上午
 * @description: 对代理重构,使用BeanPostProcessor减少代码允许在bean初始化前后做处理
 */
@Component
public class MyAutowiredAnnotationPostProcessor
    implements BeanPostProcessor, ApplicationContextAware {

  private static ApplicationContext context;

  @SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    ReflectiveUtil.inject(bean, context,null, MyAutowired.class);
    return bean;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}

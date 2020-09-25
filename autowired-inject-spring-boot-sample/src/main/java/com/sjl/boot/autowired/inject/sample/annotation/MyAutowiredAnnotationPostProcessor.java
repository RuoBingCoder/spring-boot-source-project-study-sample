package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.proxy.RpcServiceFactory;
import com.sjl.boot.autowired.inject.sample.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:45 上午
 * @description: 对代理重构,使用BeanPostProcessor减少代码允许在bean初始化前后做处理
 */
@Component
public class MyAutowiredAnnotationPostProcessor implements BeanPostProcessor {


  @SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
      ReflectiveUtil.inject(bean,null,MyAutowired.class);
    return bean;
  }

}

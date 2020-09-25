package com.sjl.boot.autowired.inject.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringValueResolver;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class BootAutowiredInjectSampleApplicationTests  {
  @Resource
  private DefaultListableBeanFactory beanFactory;
  @Test
  void contextLoads() {
    Map<String, StringValueResolver> beans = beanFactory.getBeansOfType(StringValueResolver.class);

    //    String value = valueResolver.resolveStringValue("2 * * * * *");
    beans.forEach(
        (k, v) -> System.out.println("k is:"+k.toString()+"-> v is:"+v.toString()));
  }
}



package com.sjl.bean.life.cycle.bean;

import com.sjl.bean.life.cycle.constants.Constant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;

/**
 * @author: JianLei
 * @date: 2020/9/8 11:59 上午
 * @description:
 */
@Component
public class BeanPostProcessorImpl implements BeanPostProcessor,Ordered, ApplicationContextAware {
  private ApplicationContext applicationContext;
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (bean instanceof Pig) {
      Pig pig = (Pig) bean;
      Constant.beansMap.put(pig.getClass().getSimpleName(),pig);
      System.out.println("$pig class name is:" + pig.getClass().getName());
    }
   /* System.out.println(
        Constant.count.incrementAndGet()
            + "、->[BeanPostDemo  postProcess[Before]Initialization 执行! ]");*/
    return null;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
   /* System.out.println(
        Constant.count.incrementAndGet()
            + "、->[BeanPostDemo  postProcess[After]Initialization 执行! ]");*/
    return null;
  }


  @Override
  public int getOrder() {
    return -3;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    System.out.println(Constant.count.incrementAndGet()+"、->开始setter");
    this.applicationContext=applicationContext;
  }
}

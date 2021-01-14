package com.github.custom.aop.annotation;

import com.github.custom.aop.factory.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/29 11:17 上午
 * @description:
 *     <p>主要针对cglib代理bean做代理,注意由于优先级问题, 自定义代理bean暂时不能让容器帮忙实现依赖注入 即不能用@Autowired注解 实现{@link
 *     BeanPostProcessor}接口注意优先级问题{@link PriorityOrdered}高于{@link org.springframework.core.Ordered}
 *     {@link AutowiredAnnotationBeanPostProcessor}
 */
@Component
public class CglibProxyAnnotationInstancePostProcessor
    implements SmartInstantiationAwareBeanPostProcessor, PriorityOrdered {

  /**
   * bean实例化之前创建代理类
   *
   * @param beanClass
   * @param beanName
   * @return
   * @throws BeansException
   */
  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    if (beanClass.isAnnotationPresent(Cglib.class)) {
      return ProxyFactory.getCGLIBProxy(beanClass);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return -665323724;
  }
}

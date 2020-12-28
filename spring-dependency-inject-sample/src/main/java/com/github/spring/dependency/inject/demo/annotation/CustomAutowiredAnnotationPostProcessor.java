package com.github.spring.dependency.inject.demo.annotation;

import com.github.spring.dependency.inject.demo.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/13 11:45 上午
 * @description: 对代理重构, 使用BeanPostProcessor减少代码允许在bean初始化前后做处理
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor {@link #postProcessProperties(PropertyValues, Object, String)}
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory {@link #doCreateBean}
 */
@Component
public class CustomAutowiredAnnotationPostProcessor
        implements InstantiationAwareBeanPostProcessor {


    @SneakyThrows
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectiveUtil.inject(bean, CustomAutowired.class);
        return true;
    }
}

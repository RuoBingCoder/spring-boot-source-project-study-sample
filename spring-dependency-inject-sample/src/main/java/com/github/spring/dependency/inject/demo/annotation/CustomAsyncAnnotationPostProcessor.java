package com.github.spring.dependency.inject.demo.annotation;

import com.github.spring.dependency.inject.demo.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author: JianLei
 * @date: 2020/11/28 下午3:42
 * @description: MyAsyncAnnontationPostProcessor
 */

public class CustomAsyncAnnotationPostProcessor implements BeanPostProcessor {
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ReflectiveUtil.inject(bean, CustomAsync.class);
        return bean;
    }
}

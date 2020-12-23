package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/25 1:51 下午
 * @description:
 */
@Component
public class CustomValueAnnotationPostProcessor implements BeanPostProcessor {

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectiveUtil.inject(bean, CustomValue.class);
        return bean;
    }

}

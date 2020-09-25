package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.utils.ReflectiveUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/25 1:51 下午
 * @description:
 */
@Component
public class MyValueAnnotationPostProcessor implements BeanPostProcessor, EnvironmentAware {
    private Environment environment;
    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectiveUtil.inject(bean,environment,MyValue.class);
        return bean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}

package com.github.spring.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/15 4:52 下午
 * @description:
 */
@Component
public class BeanPostProcessorImplTestOrdered implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//    System.out.println("测试BeanPostProcessorImplTestOrdered PriorityOrdered 加载优先级 postProcessBeforeInitialization");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("测试BeanPostProcessorImplTestOrdered PriorityOrdered 加载优先级 postProcessAfterInitialization");
        return null;
    }

    @Override
    public int getOrder() {
        return -5;
    }
}

package com.sjl.bean.life.cycle.bean;

import com.sjl.bean.life.cycle.constants.Constant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/8 11:59 上午
 * @description:
 */
@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    System.out.println(Constant.count.incrementAndGet()+"、->[BeanPostDemo  postProcess[Before]Initialization 执行! ]");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(Constant.count.incrementAndGet()+"、->[BeanPostDemo  postProcess[After]Initialization 执行! ]");
        return null;
    }
}

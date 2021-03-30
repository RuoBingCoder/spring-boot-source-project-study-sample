package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.annotation.SimpleOrdered;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.aware.SimpleBeanFactoryAware;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 8:06 下午
 * @description: CustomBeanPostProcessor
 */
@SimpleComponent
@SimpleOrdered(-2)
@Slf4j
public class CustomBeanPostProcessorA implements SimpleBeanPostProcessor, SimpleBeanFactoryAware {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        log.info("Before --->>>" + bean.getClass() + "->CustomBeanPostProcessor order: -2");

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        log.info("After --->>>" + bean.getClass() + "->CustomBeanPostProcessor order: -2");
        return null;
    }


    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        log.info("--->" + simpleBeanFactory.getClass());

    }
}

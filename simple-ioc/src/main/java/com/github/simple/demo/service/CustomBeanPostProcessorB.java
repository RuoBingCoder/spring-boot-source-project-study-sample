package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.annotation.SimpleOrdered;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleBeanFactoryAware;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 8:06 下午
 * @description: CustomBeanPostProcessor
 */
@SimpleComponent
@SimpleOrdered(-10)
@Slf4j
public class CustomBeanPostProcessorB implements SimpleBeanPostProcessor, SimpleBeanFactoryAware {
    private SimpleBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
         log.info("Before --->>>"+bean.getClass()+"->CustomBeanPostProcessor2 order: -10");

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
         log.info("After --->>>"+bean.getClass()+"->CustomBeanPostProcessor2 order: -10");
        return null;
    }




    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        this.beanFactory=simpleBeanFactory;
         log.info("--->"+beanFactory.getClass());

    }
}

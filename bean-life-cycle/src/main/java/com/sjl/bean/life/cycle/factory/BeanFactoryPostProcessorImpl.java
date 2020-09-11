package com.sjl.bean.life.cycle.factory;

import com.sjl.bean.life.cycle.constants.Constant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/8 12:01 下午
 * @description:
 */
@Component
public class BeanFactoryPostProcessorImpl implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory clb) throws BeansException {
        System.out.println(Constant.count.incrementAndGet()+"、->[BeanPostFactoryDemo  postProcessBeanFactory 执行! ]");

    }
}

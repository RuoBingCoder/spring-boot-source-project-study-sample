package com.sjl.bean.life.cycle.factory;

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
        //注意只能用于依赖注入,不能用于依赖查找
        clb.registerResolvableDependency(String.class,"hello word");
        System.out.println("==================BeanFactoryPostProcessorImpl=========================");
        //一旦调用getBean会对bean进行初始化
//        Pig pig = (Pig) clb.getBean("pig");
//        System.out.println("------------->>>>>"+pig.toString());
//        System.out.println(Constant.count.incrementAndGet()+"、->[BeanPostFactoryDemo  postProcessBeanFactory 执行! ]");

    }
}

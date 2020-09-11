package com.sjl.bean.life.cycle.merge;

import com.sjl.bean.life.cycle.constants.Constant;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/11 5:33 下午
 * @description:
 */
@Component
public class MergedBeanDefinitionPostProcessorImpl implements MergedBeanDefinitionPostProcessor {
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
    System.out.println(Constant.count.incrementAndGet()+"、->MergedBeanDefinitionPostProcessorImpl postProcessMergedBeanDefinition()");
    }

    @Override
    public void resetBeanDefinition(String beanName) {

    }
}

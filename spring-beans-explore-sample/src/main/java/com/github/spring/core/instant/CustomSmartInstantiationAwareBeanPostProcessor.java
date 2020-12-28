package com.github.spring.core.instant;

import com.github.spring.core.bean.Cat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/23 下午2:31
 * @description: CustomSmartInstantiationAwareBeanPostProcessor
 * {@link DefaultListableBeanFactory #getBeanNamesForType()}
 */
@Component
@Slf4j
public class CustomSmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    /**
     * @return
     * @Author jianlei.shi
     * @Description 执行顺序在
     * @Date 下午2:38 2020/11/23
     * @Param
     **/
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass.isAssignableFrom(Cat.class)) {
            log.info("==>this is bean predictBeanType is:{} bean name is:{}", beanClass.getName(), beanName);
        }
        return beanClass;
    }

}

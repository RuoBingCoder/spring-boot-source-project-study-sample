package com.github.simple.core.annotation;

import com.github.simple.core.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.BeansException;

/**
 * @author: JianLei
 * @date: 2020/12/14 5:41 下午
 * @description: SimpleBeanDefinitionRegistry
 */
public interface SimpleBeanDefinitionRegistryPostProcessor extends SimpleBeanFactoryPostProcessor {

    void postProcessBeanDefinitionRegistry(SimpleBeanDefinitionRegistry registry) throws BeansException;

}

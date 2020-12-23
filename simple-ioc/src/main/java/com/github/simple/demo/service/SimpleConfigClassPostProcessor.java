package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleBeanDefinitionRegistryPostProcessor;
import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.beans.factory.SimpleConfigBeanFactory;
import com.github.simple.core.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.BeansException;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 7:17 下午
 * @description: SimpleConfigClassPostProcessor
 */
@SimpleComponent
public class SimpleConfigClassPostProcessor implements SimpleBeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(SimpleBeanDefinitionRegistry registry) throws BeansException {
        registry.registerBeanDefinition("registryBean",getRegistryBeanDef());
    }

    private SimpleRootBeanDefinition getRegistryBeanDef() {
       return SimpleRootBeanDefinition.builder().beanName("registryBean").rootClass(RegistryBean.class).isSingleton(true).build();
    }

    @Override
    public void postProcessBeanFactory(SimpleConfigBeanFactory factory) {

    }


}

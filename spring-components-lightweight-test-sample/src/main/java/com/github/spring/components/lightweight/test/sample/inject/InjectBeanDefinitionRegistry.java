package com.github.spring.components.lightweight.test.sample.inject;

import com.github.spring.components.lightweight.test.sample.service.ServiceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/2/10 4:44 下午
 * @description 测试dubbo serviceBean 注入Ref 属性,即服务的实现类
 */
@Component
public class InjectBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder bd = BeanDefinitionBuilder.genericBeanDefinition("serviceBean");
        bd.addPropertyReference("ref", "refService");
        AbstractBeanDefinition beanDefinition = bd.getBeanDefinition();
        beanDefinition.setBeanClass(ServiceBean.class);
        registry.registerBeanDefinition("serviceBean", beanDefinition);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

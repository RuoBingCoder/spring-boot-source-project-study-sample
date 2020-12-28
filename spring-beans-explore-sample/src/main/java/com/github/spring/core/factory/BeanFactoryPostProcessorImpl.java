package com.github.spring.core.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/8 12:01 下午
 * @description:
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions
 * @see org.springframework.context.annotation.ConfigurationClassParser#parse(Class, String)
 * <pre>
 *     注解{@code @Component}解析{@link org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass}
 * </pre>
 */
@Component
public class BeanFactoryPostProcessorImpl implements BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory clb) throws BeansException {

        //注意只能用于依赖注入,不能用于依赖查找
        clb.registerResolvableDependency(String.class, "hello word");
        System.out.println("==================BeanFactoryPostProcessorImpl=========================");
        //一旦调用getBean会对bean进行初始化
//        Pig pig = (Pig) clb.getBean("pig");
//        System.out.println("------------->>>>>"+pig.toString());
//        System.out.println(Constant.count.incrementAndGet()+"、->[BeanPostFactoryDemo  postProcessBeanFactory 执行! ]");

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }
}

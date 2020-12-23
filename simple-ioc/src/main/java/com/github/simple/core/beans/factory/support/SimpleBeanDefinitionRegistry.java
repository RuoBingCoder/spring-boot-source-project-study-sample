package com.github.simple.core.beans.factory.support;

import com.github.simple.core.definition.SimpleRootBeanDefinition;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * @author: JianLei
 * @date: 2020/12/21 7:27 下午
 * @description: SimpleBeanDefinitionRegistry
 */
public interface SimpleBeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, SimpleRootBeanDefinition beanDefinition);


    void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    SimpleRootBeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * Return the names of all beans defined in this registry.
     * @return the names of all beans defined in this registry,
     * or an empty array if none defined
     */
    String[] getBeanDefinitionNames();

}

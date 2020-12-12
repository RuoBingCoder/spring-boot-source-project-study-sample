package com.github.simple.ioc.factory;

import com.github.simple.ioc.definition.SimpleRootBeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:01 下午
 * @description: SimpleDefaultListableBeanFactory
 */

public class SimpleDefaultListableBeanFactory {
    private final SimpleAutowiredCapableBeanFactory factory;

    public SimpleDefaultListableBeanFactory() throws Throwable {
        this.factory = new SimpleAutowiredCapableBeanFactory();
        finishBeanInstance();
    }

    public <T> T getBean(Class<T> clazz) throws Throwable {
        return (T) factory.getBean(clazz);
    }

    public Object getBean(String name) throws Throwable {
        return factory.getBean(name);
    }

    public Map<String,Object> getBeans(){
        return factory.getBeans();
    }

    public void finishBeanInstance() throws Throwable {
        Map<String, SimpleRootBeanDefinition> beanDefinitionMap = factory.beanDefinitionMap;
        List<String> beanNames = new ArrayList<>(beanDefinitionMap.keySet());
        for (String beanName : beanNames) {
            factory.getBean(beanName);
        }

    }
}

package com.github.simple.core.beans.factory.support;

import cn.hutool.core.lang.Assert;
import com.github.simple.core.annotation.SimpleAspect;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleBeanFactoryAware;
import com.github.simple.core.beans.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.core.definition.SimpleRootBeanDefinition;

/**
 * @author: jianlei.shi
 * @date: 2020/12/24 2:14 下午
 * @description: SimpleBeanFactorySupport
 */

public class SimpleBeanFactorySupport implements SimpleBeanFactoryAware {
    private static SimpleDefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        SimpleBeanFactorySupport.beanFactory = (SimpleDefaultListableBeanFactory) simpleBeanFactory;
    }

    public static Class<?> matchAspect(String name) {
        SimpleRootBeanDefinition mbd = beanFactory.getBeanDefinitions().get(name);
        Assert.notNull(mbd);
        if (mbd.getBeanClass().isAnnotationPresent(SimpleAspect.class)) {
            return mbd.getBeanClass();
        }
        return null;
    }

    public static String[] getAllBeanName() {
        return beanFactory.getBeanDefinitionNames();
    }

    public static SimpleDefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}

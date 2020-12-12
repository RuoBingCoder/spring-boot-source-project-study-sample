package com.sjl.spring.components.pojo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/12/4 下午3:14
 * @description: FactoryBean解析并获取 {@link FactoryBean#getObject}添加到容器中
 * @see org.springframework.beans.factory.support.AbstractBeanFactory#getObjectForBeanInstance
 * @see org.springframework.beans.factory.support.FactoryBeanRegistrySupport#getObjectFromFactoryBean
 * @see org.springframework.beans.factory.support.FactoryBeanRegistrySupport#doGetObjectFromFactoryBean
 */
@Component
public class CustomFactoryBean implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        return new Hello();
    }

    @Override
    public Class<?> getObjectType() {
        return Hello.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

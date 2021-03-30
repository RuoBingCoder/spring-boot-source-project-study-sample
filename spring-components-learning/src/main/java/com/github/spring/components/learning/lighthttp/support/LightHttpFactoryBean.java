package com.github.spring.components.learning.lighthttp.support;

import com.github.exception.CommonException;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author jianlei.shi
 * @date 2021/2/25 11:01 上午
 * @description LightHttpFactoryBean
 */

public class LightHttpFactoryBean<T> extends LightHttpSupport implements FactoryBean<T> {

    private Class<T> interfaces;

    public LightHttpFactoryBean() {
    }

    public LightHttpFactoryBean(Class<T> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public T getObject() {
        Object proxy = createProxy(interfaces);
        return (T) proxy;
    }


    @Override
    public void checkInterfaces() {
        if (interfaces == null) {
            throw new CommonException("LightHttpFactoryBean  interfaces is null");
        }
    }

    @Override
    public Class<?> getObjectType() {
        return interfaces;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }




}

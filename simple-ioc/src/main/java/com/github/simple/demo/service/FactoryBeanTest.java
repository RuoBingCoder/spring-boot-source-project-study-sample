package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.beans.SimpleFactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: jianlei.shi
 * @date: 2020/12/19 3:37 下午
 * @description: FactoryBeanTest
 */
@SimpleComponent
public class FactoryBeanTest implements SimpleFactoryBean<FactoryBean> {

    private static Type type;

    @Override
    public FactoryBean getObject() {
        return new FactoryBean();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static void main(String[] args) {
        Type[] types = FactoryBeanTest.class.getGenericInterfaces();
        ParameterizedType type= (ParameterizedType) types[0];
        Type[] arguments = type.getActualTypeArguments();

        System.out.println(arguments[0].getTypeName());
    }

}

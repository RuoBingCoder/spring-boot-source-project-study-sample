package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.beans.SimpleFactoryBean;

/**
 * @author: jianlei.shi
 * @date: 2020/12/19 3:37 下午
 * @description: FactoryBeanTest
 */
@SimpleComponent
public class FactoryBeanTest2 implements SimpleFactoryBean<FactoryBeanB> {


    @Override
    public FactoryBeanB getObject() {
        return new FactoryBeanB();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


}

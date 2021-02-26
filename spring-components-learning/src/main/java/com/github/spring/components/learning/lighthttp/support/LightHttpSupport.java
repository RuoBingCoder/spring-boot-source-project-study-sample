package com.github.spring.components.learning.lighthttp.support;

import com.github.spring.components.learning.lighthttp.handler.LightHttpInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import utils.ProxyFactory;

/**
 * @author jianlei.shi
 * @date 2021/2/26 11:43 上午
 * @description LightHttpSupport
 */

public abstract class LightHttpSupport implements InitializingBean, BeanFactoryAware {

    private ConfigurableBeanFactory beanFactory;

    protected Object createProxy(Class<?> interfaces) {
        return ProxyFactory.getProxy(interfaces, new LightHttpInvocationHandler(interfaces, beanFactory));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableBeanFactory) {
            this.beanFactory = (ConfigurableBeanFactory) beanFactory;
        }
    }

    @Override
    public void afterPropertiesSet() {
        checkInterfaces();
    }


    protected abstract void checkInterfaces();
}


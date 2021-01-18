package com.github.spring.components.fcatorybean;

import com.github.spring.components.service.FactoryBeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/12/4 下午3:14
 * @description: FactoryBean解析并获取 {@link FactoryBean#getObject} <h2>延迟加载</h2> 只有用到才会添加调用 inject 也是如此
 * @see org.springframework.beans.factory.support.AbstractBeanFactory#getObjectForBeanInstance
 * @see org.springframework.beans.factory.support.FactoryBeanRegistrySupport#getObjectFromFactoryBean
 * @see org.springframework.beans.factory.support.FactoryBeanRegistrySupport#doGetObjectFromFactoryBean
 * <P>
 *    factory {@code getBean(FactoryBeanService.class))
 *
 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory#getBean(Class)}
 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveBean(ResolvableType, Object[], boolean)}
 *
 *
 * </P>
 */
@Component
@Slf4j
public class CustomFactoryBean implements FactoryBean<FactoryBeanService> {
    public CustomFactoryBean() {
        log.info("CustomFactoryBean init!");
    }

    @Override
    public FactoryBeanService getObject() throws Exception {
        return new FactoryBeanService();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

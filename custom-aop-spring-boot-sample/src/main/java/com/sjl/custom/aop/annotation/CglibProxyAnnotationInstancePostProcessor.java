package com.sjl.custom.aop.annotation;

import com.sjl.custom.aop.factory.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/29 11:17 上午
 * @description: 主要针对cglib代理bean做代理,注意由于优先级问题,代理bean暂时不能依赖注入 即不能用@Autowired注解
 */
@Component
public class CglibProxyAnnotationInstancePostProcessor implements SmartInstantiationAwareBeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass.isAnnotationPresent(Cglib.class)){
            return ProxyFactory.getCGLIBProxy(beanClass);
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }




    @Override
    public int getOrder() {
        return -665323724;
    }
}

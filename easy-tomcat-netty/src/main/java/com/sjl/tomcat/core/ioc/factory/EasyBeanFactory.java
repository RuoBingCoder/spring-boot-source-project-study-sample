package com.sjl.tomcat.core.ioc.factory;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:28 下午
 * @description: EasyBeanFactory
 */
public interface EasyBeanFactory {
    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanClass) throws Exception;
}

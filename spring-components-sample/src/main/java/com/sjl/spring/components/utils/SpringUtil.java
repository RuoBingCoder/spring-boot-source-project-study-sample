package com.sjl.spring.components.utils;

import com.sjl.spring.components.exception.BeansNotFoundException;
import com.sjl.spring.components.transaction.custom.annotation.EasyAutowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/11/7 8:20 下午
 * @description: SpringUtil
 */
@Component
@Order(-2000)
@Slf4j
public class SpringUtil implements ApplicationContextAware, EnvironmentAware, InitializingBean, DisposableBean {
    private static ApplicationContext applicationContext;
    private static final Set<Object> AUTOWIRED_BEANS_SET = new LinkedHashSet<>();
    private static final Set<Object> SERVICE_BEANS_SET = new LinkedHashSet<>();
    private static final String SERVICE_NAME_SPACE = "service.namespace";
    private static final String MATCH_CONTROLLER = "match.controller";
    private Environment environment;

    public static Set<Object> getInjectBeans() {
        return AUTOWIRED_BEANS_SET;
    }

    public static Set<Object> getServicesBeans() {
        return SERVICE_BEANS_SET;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }


    public static <T> T getBeanByName(String name) {
        Object bean = applicationContext.getBean(name);
        if (bean == null) {
            throw new BeansNotFoundException("this bean not found!-->>" + name);
        }
        return (T) bean;

    }

    public static <T> T getBeanByType(Class<?> type) {
        Object bean = applicationContext.getBean(type);
        return (T) bean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private void getMyAutowiredBeans() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            try {
                Object bean = null;
                if (beanDefinitionName.startsWith(
                        Objects.requireNonNull(environment.getProperty(SERVICE_NAME_SPACE)))) {
                    //一旦getBean 则不会走后置处理器
                    bean = applicationContext.getBean(beanDefinitionName);
                    SERVICE_BEANS_SET.add(bean);
                }
            } catch (Exception e) {
                log.error("getMyAutowiredBeansSet error:", e);
                throw new RuntimeException("getMyAutowiredBeansSet error");
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("-----MyAutowired bean set 初始化完毕!--------");
        getMyAutowiredBeans();
    }

    @Override
    public void destroy() throws Exception {
        log.info("=============>开始清空缓存<==============");
        AUTOWIRED_BEANS_SET.clear();
        SERVICE_BEANS_SET.clear();
    }

}

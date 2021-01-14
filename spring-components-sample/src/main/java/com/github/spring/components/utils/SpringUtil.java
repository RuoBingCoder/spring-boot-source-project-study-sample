package com.github.spring.components.utils;

import com.github.spring.components.exception.BeansNotFoundException;
import com.github.spring.components.transaction.custom.annotation.EasyTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

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
    public static Map<Class<?>, Object> tomcatBean = new HashMap<>();

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


    /**
     * @author jianlei.shi
     * @description
     * @date 6:02 下午 2020/12/31
     * @param null
     * @return
     **/
    public static <T> T getBeanByType(Class<?> type) {

        applicationContext.getBeansOfType(null);
        Object bean = applicationContext.getBean(type);
        return (T) bean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;

    }

    /**
     * 一旦getBean 创建bean走
     * <p>
     *
     * @Nullable protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
     * Object bean = null;
     * if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
     * // Make sure bean class is actually resolved at this point.
     * if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
     * Class<?> targetType = determineTargetType(beanName, mbd);
     * if (targetType != null) {
     * bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
     * if (bean != null) {
     * bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
     * }
     * }
     * }
     * mbd.beforeInstantiationResolved = (bean != null);
     * }
     * return bean;
     * </p>
     */
    private void getMyAutowiredBeans() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            try {
                Object bean = null;
                if (beanDefinitionName.startsWith(
                        Objects.requireNonNull(environment.getProperty(SERVICE_NAME_SPACE)))) {
                    bean = applicationContext.getBean(beanDefinitionName);
                    if (isNeedProxy(bean)) {
                        SERVICE_BEANS_SET.add(bean);
                    }
                }
            } catch (Exception e) {
                log.error("getMyAutowiredBeansSet error:", e);
                throw new RuntimeException("getMyAutowiredBeansSet error");
            }
        }
    }

    private boolean isNeedProxy(Object bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isSynthetic()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(EasyTransactional.class)) {
                    return true;
                }
            }
        }
        return false;
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

    /**
     * 依赖查找
     * @see org.springframework.beans.factory.ObjectFactory#getObject
     */
    public static void dependencyLookup() {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        //getIfAvailable 如果是空不会报错
        System.out.println(beanProvider.getIfAvailable());
        //空的话则会报错
        beanProvider.stream().forEach(System.out::println);
    }

}

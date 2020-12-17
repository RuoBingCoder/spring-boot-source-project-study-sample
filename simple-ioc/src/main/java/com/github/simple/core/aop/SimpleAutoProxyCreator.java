package com.github.simple.core.aop;

import com.github.simple.core.annotation.SimpleOrdered;
import com.github.simple.core.annotation.SimpleSmartInstantiationAwareBeanPostProcessor;
import com.github.simple.core.exception.SimpleProxyCreateException;
import com.github.simple.core.factory.SimpleProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 7:44 下午
 * @description: 代理
 */
@SimpleOrdered(-90)
@Slf4j
public class SimpleAutoProxyCreator implements SimpleSmartInstantiationAwareBeanPostProcessor {

    private List<SimpleAdviseSupport> simpleAdviseSupports;

    private final Map<String, Object> cacheBeans = new ConcurrentHashMap<>();

    public static final String POINT_CUT = "SimplePointCut";

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (isEligibleAdvisors(bean)) {
            Object proxyBean;
            log.info("开始创建代理 beanName :{}",beanName);
            try {
                proxyBean = createProxy(bean);
                cacheBeans.put(beanName, proxyBean);
            } catch (Exception e) {
                cacheBeans.clear();
                throw new SimpleProxyCreateException("createProxy exception info : " + e.getMessage());
            }
            return proxyBean;
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }


    private Object createProxy(Object bean) {
        return SimpleProxyFactory.getProxy().createCGLIBProxy(bean.getClass());
    }

    /**
     * 简单的处理
     *
     * @param bean
     * @return
     */
    private boolean isEligibleAdvisors(Object bean) {
        for (SimpleAdviseSupport support : getSimpleAdviseSupports()) {
            String info = support.getAllAspectMethods().stream().filter(m -> m.getAnnotationName().equals(POINT_CUT)).map(SimpleAdviseSupport.MethodWrapper::getAnnotationInfo).collect(Collectors.joining());
            //简单处理 TODO 正则匹配
            return bean.getClass().getName().startsWith(info);

        }
        return false;
    }

    public List<SimpleAdviseSupport> getSimpleAdviseSupports() {
        return simpleAdviseSupports;
    }

    public void setSimpleAdviseSupports(List<SimpleAdviseSupport> simpleAdviseSupports) {
        this.simpleAdviseSupports = simpleAdviseSupports;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (getCacheBeanByName(beanName) != null) {
            return getCacheBeanByName(beanName);
        }
        if (isEligibleAdvisors(bean)) {
            return createProxy(bean);
        }
        return null;
    }

    public Object getCacheBeanByName(String beanName) {
        return cacheBeans.get(beanName);
    }
}

package com.github.simple.core.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.*;
import com.github.simple.core.beans.factory.SimpleProxyFactory;
import com.github.simple.core.beans.factory.support.SimpleBeanFactorySupport;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.exception.SimpleProxyCreateException;
import com.github.simple.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectJAdvisorsBuilder;
import org.springframework.beans.BeansException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 7:44 下午
 * @description: 代理
 */
@Slf4j
public class SimpleAutoProxyCreator implements SimpleSmartInstantiationAwareBeanPostProcessor {

    /**
     * 简单的通知支持集合
     */
    private List<SimpleAdviseSupport> simpleAdviseSupports = new ArrayList<>();

    /**
     * aspect类型缓存
     */
    private final Map<String, Boolean> aspectTypeCache = new ConcurrentHashMap<>(128);

    /**
     * 缓存代理bean
     */
    private final Map<String, Object> cacheProxyBeans = new ConcurrentHashMap<>();

    public static final String POINT_CUT = "SimplePointCut";

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (isEligibleAdvisors(bean)) {
            Object proxyBean;
            log.info("开始创建代理 beanName :{}", beanName);
            try {
                proxyBean = createProxy(bean);
                cacheProxyBeans.put(beanName, proxyBean);
            } catch (Exception e) {
                cacheProxyBeans.clear();
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
        if (CollectionUtil.isEmpty(getSimpleAdviseSupports())) {
            return false;
        }
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
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        if (hasCacheBean(beanName)) {
            return getCacheBean(beanName);
        }
        if (shouldSkip(bean, beanName)) {
            return null;
        }
        return createProxy(bean);
    }

    private Object getCacheBean(String beanName) {
        return cacheProxyBeans.get(beanName);
    }

    /**
     * @param bean instance
     * @param beanName bean name
     * @return proxy
     * @see org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator#shouldSkip
     * @see AnnotationAwareAspectJAutoProxyCreator#findCandidateAdvisors
     * @see org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper#findAdvisorBeans
     * @see BeanFactoryAspectJAdvisorsBuilder#buildAspectJAdvisors()
     */
    private boolean shouldSkip(Object bean, String beanName) {
        if (SimpleBeanFactorySupport.getBeanFactory() == null) {
            return true;
        }
        String[] allBeanNames = SimpleBeanFactorySupport.getAllBeanNames();
        if (allBeanNames == null || allBeanNames.length <= 0) {
            throw new SimpleIOCBaseException("the beanNames is empty!");
        }
        for (String name : allBeanNames) {
            Class<?> aspectBeanClass = SimpleBeanFactorySupport.matchAspect(name);
            if (aspectBeanClass != null) {
                if (!aspectTypeCache.containsKey(name)) {
                    parseAspect(aspectBeanClass);
                    aspectTypeCache.put(name, Boolean.FALSE);
                }
            }

        }

        return !isEligibleAdvisors(bean);
    }

    /**
     * 是否有缓存bean
     *
     * @param beanName bean的名字
     * @return boolean
     */
    public boolean hasCacheBean(String beanName) {
        return getCacheBean(beanName) == null;
    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> clazz, String beanName) {
        return null;
    }


    /**
     * 解析切面
     *
     * @param clazz clazz
     */
    private void parseAspect(Class<?> clazz) {
        if (ReflectUtils.matchAspect(clazz)) {
            List<SimpleAdviseSupport.MethodWrapper> methods = new ArrayList<>(3);
            Map<Method, SimplePointCut> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimplePointCut.class);
            if (CollectionUtil.isNotEmpty(methodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(methodAndAnnotation);
                methods.add(methodWrapper);
            }
            Map<Method, SimpleBefore> beforeMethodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimpleBefore.class);
            if (CollectionUtil.isNotEmpty(beforeMethodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(beforeMethodAndAnnotation);
                methods.add(methodWrapper);
            }
            Map<Method, SimpleAfter> afterMethodAndAnnotation = ReflectUtils.getMethodAndAnnotation(clazz, SimpleAfter.class);
            if (CollectionUtil.isNotEmpty(afterMethodAndAnnotation)) {
                SimpleAdviseSupport.MethodWrapper methodWrapper = getMethodWrapper(afterMethodAndAnnotation);
                methods.add(methodWrapper);
            }
            SimpleAdviseSupport simpleAdviseSupport = new SimpleAdviseSupport(methods);
            simpleAdviseSupports.add(simpleAdviseSupport);
        }
    }

    /**
     * get方法包装
     *
     * @param methodAndAnnotation 法和注释
     * @return {@link MethodWrapper}
     */
    private <T extends Annotation> SimpleAdviseSupport.MethodWrapper getMethodWrapper(Map<Method, T> methodAndAnnotation) {
        Iterator<Method> iterator = methodAndAnnotation.keySet().iterator();
        Method method = null;
        while (iterator.hasNext()) {
            method = iterator.next();
        }
        assert method != null;
        T annotation = methodAndAnnotation.get(method);
        String annotationMeta = null;
        String annotationName = null;
        if (annotation instanceof SimplePointCut) {
            SimplePointCut spc = (SimplePointCut) annotation;
            annotationMeta = spc.expression();
            annotationName = SimpleIOCConstant.SIMPLE_POINT_CUT;
        } else if (annotation instanceof SimpleBefore) {
            SimpleBefore spc = (SimpleBefore) annotation;
            annotationMeta = spc.value();
            annotationName = SimpleIOCConstant.SIMPLE_BEFORE;
        } else if (annotation instanceof SimpleAfter) {
            SimpleAfter spc = (SimpleAfter) annotation;
            annotationMeta = spc.value();
            annotationName = SimpleIOCConstant.SIMPLE_AFTER;

        }

        return new SimpleAdviseSupport.MethodWrapper(method.getName()
                , annotationName
                , annotationMeta, method);
    }
}

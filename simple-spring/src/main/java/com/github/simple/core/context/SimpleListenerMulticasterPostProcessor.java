package com.github.simple.core.context;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.EventListener;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.context.aware.SimpleApplicationContextAware;
import com.github.simple.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 简单的侦听器多播后处理器
 *
 * @author jianlei.shi
 * @date 2021/1/18 3:12 下午
 * @description EventMulticasterPostProcessor
 */
@Slf4j
public class SimpleListenerMulticasterPostProcessor implements SimpleBeanPostProcessor, SimpleApplicationContextAware {
    public static final String SIMPLE_LISTENER_MULTICASTER_BEAN_NAME="simpleListenerMulticaster";
    private SimpleApplicationContext simpleApplicationContext;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof SimpleApplicationListener) {
            SimpleApplicationListener simpleApplicationListener = (SimpleApplicationListener) bean;
            simpleApplicationContext.addApplicationListener(simpleApplicationListener);
        }
        final Map<Method, EventListener> methodEventListenerMap = ReflectUtils.getMethodAndAnnotation(bean.getClass(), EventListener.class);
        if (CollectionUtil.isNotEmpty(methodEventListenerMap)){
            final SimpleEventListenerMethodAdapter simpleEventListenerMethodAdapter = new SimpleEventListenerMethodAdapter(simpleApplicationContext, getMethod(methodEventListenerMap), beanName,getEventType(getMethod(methodEventListenerMap)));
            simpleApplicationContext.addApplicationListener(simpleEventListenerMethodAdapter);
        }
        return null;
    }

    private Class<?> getEventType(Method method) {
       return ReflectUtils.getMethodParameter(method);
    }

    private Method getMethod(Map<Method, EventListener> methodAndAnnotation) {
        return methodAndAnnotation.keySet().stream().findFirst().orElse(null);
    }

    @Override
    public void setApplicationContext(SimpleApplicationContext simpleApplicationContext) {
        log.info("开始获取SimpleApplicationContext->:{}",simpleApplicationContext==null?"null":simpleApplicationContext.getClass());
        this.simpleApplicationContext = simpleApplicationContext;
    }
}

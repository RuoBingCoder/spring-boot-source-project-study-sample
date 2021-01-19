package com.github.simple.core.context;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.EventListener;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/1/18 3:12 下午
 * @description EventMulticasterPostProcessor
 */
@Slf4j
public class SimpleListenerMulticasterPostProcessor implements SimpleBeanPostProcessor, SimpleApplicationContextAware {
    private SimpleApplicationContext simpleApplicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        if (bean instanceof SimpleApplicationListener) {
            SimpleApplicationListener simpleApplicationListener = (SimpleApplicationListener) bean;
            simpleApplicationContext.addApplicationListener(simpleApplicationListener);
        }
        final Map<Method, EventListener> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(bean.getClass(), EventListener.class);
        if (CollectionUtil.isNotEmpty(methodAndAnnotation)){
            final SimpleEventListenerMethodAdapter simpleEventListenerMethodAdapter = new SimpleEventListenerMethodAdapter(simpleApplicationContext, getMethod(methodAndAnnotation), beanName,getEventType(getMethod(methodAndAnnotation)));
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

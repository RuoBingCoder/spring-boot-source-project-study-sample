package com.github.simple.core.context.event;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.beans.factory.SimpleConfigBeanFactory;
import com.github.simple.core.context.SimpleApplicationEvent;
import com.github.simple.core.context.SimpleApplicationListener;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.utils.ReflectUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jianlei.shi
 * @date 2021/1/18 1:29 下午
 * @description StandardSimpleApplicationEventMulticaster
 */

public class StandardSimpleApplicationEventMulticaster implements SimpleApplicationEventMulticaster {
    private final Set<SimpleApplicationListener<?>> simpleApplicationListeners = new HashSet<>();
    private SimpleConfigBeanFactory beanFactory;

    public StandardSimpleApplicationEventMulticaster(SimpleConfigBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public StandardSimpleApplicationEventMulticaster() {
    }

    /**
     * 添加应用程序侦听器
     *
     * @param listener 侦听器
     */
    @Override
    public void addApplicationListener(SimpleApplicationListener<?> listener) {
        this.simpleApplicationListeners.add(listener);
    }

    /**
     * 多播事件
     *
     * @param event 事件
     */
    @Override
    public void multicastEvent(SimpleApplicationEvent event) throws Throwable {
        doInvokerListeners(event);
    }

    /**
     * 调用程序做听众
     *
     * @param event 事件
     */
    private void doInvokerListeners(SimpleApplicationEvent event) throws Throwable {
        final Set<SimpleApplicationListener<?>> appListeners = getAppListeners(event);
        if (CollectionUtil.isNotEmpty(appListeners)) {
            for (SimpleApplicationListener appListener : appListeners) {
                appListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 让应用程序侦听器
     *
     * @param event 事件
     * @return {@link Set<SimpleApplicationListener<?>}
     */
    private Set<SimpleApplicationListener<?>> getAppListeners(SimpleApplicationEvent event) {
        if (CollectionUtil.isEmpty(simpleApplicationListeners)){
            throw new SimpleIOCBaseException("simpleApplicationListeners is empty!");
        }
        //注解事件发布 @EventListener
        final Object source = event.getSource();

//        if (event.getClass().equals(SimpleApplicationEvent.class)) {
//            return simpleApplicationListeners.stream().filter(s -> isMatchMethodParamType(s, event)).collect(Collectors.toSet());
//        }
        return simpleApplicationListeners.stream().filter(s ->
                matchEventType(s, event)).collect(Collectors.toSet());
    }

    /**
     * 比赛事件类型
     *
     * @param listener 侦听器
     * @param event    事件
     * @return boolean
     */
    private boolean matchEventType(SimpleApplicationListener<?> listener, SimpleApplicationEvent event) {
        Class<?> eventClass = getGenericParamType(listener);
        if (eventClass==SimpleApplicationEvent.class){
            return true;
        }
        return eventClass == (event.getClass());
        //方法注解处理 todo
    }

    private boolean isMatchMethodParamType(SimpleApplicationListener<?> listener, SimpleApplicationEvent event) {
        return ReflectUtils.matchMethodParameterType(listener.getClass(), SimpleApplicationEvent.class);
    }

    /**
     * 得到泛型的参数类型
     *
     * @param listener 侦听器
     * @return {@link Class<?>}
     */
    private Class<?> getGenericParamType(SimpleApplicationListener<?> listener) {
        final Object genericSingleType = ReflectUtils.getGenericSingleType(listener.getClass());
        if (SimpleApplicationEvent.class.isAssignableFrom((Class<?>) genericSingleType)) {
            return (Class<?>) genericSingleType;
        }
        throw new SimpleIOCBaseException("no such event!");
    }


}

package com.github.simple.core.context;

import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/1/19 5:25 下午
 * @description SimpleDelegateEventListener
 */

public class SimpleEventListenerMethodAdapter implements SimpleApplicationListener<SimpleApplicationEvent>{

    private final SimpleApplicationContext applicationContext;
    private final Method method;
    private final String beanName;
    private final Class<?> eventType;

    public SimpleEventListenerMethodAdapter(SimpleApplicationContext applicationContext, Method method, String beanName, Class<?> eventType) {
        this.applicationContext = applicationContext;
        this.method = method;
        this.beanName = beanName;
        this.eventType = eventType;
    }

    @Override
    public void onApplicationEvent(SimpleApplicationEvent event) throws Throwable {
        doInvoker(event);
    }

    private void doInvoker(SimpleApplicationEvent event) throws Throwable {
        final Object bean = applicationContext.getBean(beanName);
        if (eventType==event.getClass()){
            method.invoke(bean,getArgs(event));
        }
    }

    private Object[] getArgs(SimpleApplicationEvent event) {
        return new Object[]{event};
    }
}

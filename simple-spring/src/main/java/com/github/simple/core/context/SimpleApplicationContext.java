package com.github.simple.core.context;

import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.utils.SimpleStringValueResolver;

import java.lang.reflect.Field;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 4:27 下午
 * @description: SimpleApplicationContext
 */

public class SimpleApplicationContext extends AbsSimpleApplicationContext{




    public static SimpleApplicationContext run(Class<?> clazz) throws Throwable {
        return new SimpleApplicationContext(clazz);
    }

    protected SimpleApplicationContext(Class<?> sourceClass) throws Throwable {
        super(sourceClass);
    }

    @Override
    public SimpleBeanFactory getBeanFactory() {
        return super.getBeanFactory();
    }


    @Override
    protected void finishRefresh() throws Throwable {
        publishEvent(new SimpleContextRefreshEvent(this));
    }

    @Override
    public void addApplicationListener(SimpleApplicationListener<?> listener) {
        super.applicationEventMulticaster.addApplicationListener(listener);
    }


    @Override
    public <T> T getBean(Class<?> clazz) throws Throwable {
        return beanFactory.getBean(clazz);
    }

    @Override
    public <T> T getBean(String name) throws Throwable {
        return beanFactory.getBean(name);
    }

    @Override
    public Object resolveDependency(Field type, String beanName) throws Throwable {
        return beanFactory.resolveDependency(type, beanName);
    }

    @Override
    public void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor) {
        beanFactory.addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        beanFactory.setClassLoader(classLoader);

    }

    @Override
    public void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue) {
        beanFactory.registerResolvableDependency(dependencyType, autowiredValue);
    }

    @Override
    public void addBeanFactoryPostProcessor(SimpleBeanFactoryPostProcessor beanFactoryPostProcessor) {
        beanFactory.addBeanFactoryPostProcessor(beanFactoryPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(SimpleStringValueResolver valueResolver) {
        super.addEmbeddedValueResolver(valueResolver);
    }
}

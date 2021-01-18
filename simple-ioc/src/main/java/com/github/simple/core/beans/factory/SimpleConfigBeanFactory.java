package com.github.simple.core.beans.factory;

import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.context.SimpleApplicationContext;
import com.github.simple.core.context.SimpleApplicationEventPublisher;
import com.github.simple.core.utils.SimpleStringValueResolver;
import org.springframework.lang.Nullable;

/**
 * @author: JianLei
 * @date: 2020/12/19 8:29 下午
 * @description: SimpleConfigBeanFactory
 */
public interface SimpleConfigBeanFactory extends SimpleBeanFactory, SimpleApplicationEventPublisher {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor);


    void setClassLoader(ClassLoader classLoader);

    void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

    void addBeanFactoryPostProcessor(SimpleBeanFactoryPostProcessor beanFactoryPostProcessor);


    default void addEmbeddedValueResolver(SimpleStringValueResolver valueResolver) {
        return;
    }

    default String resolveEmbeddedValue(String value) {
        return null;
    }


    default <T> void addPropertySource(T source) {
        return;
    }
    
    default void setApplicationContext(SimpleApplicationContext applicationContext){
        return;
    }


}

package com.github.simple.core.config;

import com.github.simple.core.annotation.SimpleBeanMethod;
import com.github.simple.core.annotation.SimpleMethodMetadata;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleBeanFactoryAware;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 2:52 下午
 * @description: SimpleConfigBean
 */

public class SimpleConfigBean extends SimpleConfigBeanMetadata implements SimpleBeanFactoryAware {
    private SimpleBeanFactory beanFactory;

    public Object invoker(String name) throws Throwable {
        SimpleBeanMethod beanMethods = getBeanMethodByMethodName(name);
        List<SimpleMethodMetadata> simpleMethodMetadata = beanMethods.getMethodMetadata().stream().filter(m -> m.getMethodName().equals(name)).collect(Collectors.toList());
        Object bean = beanFactory.getBean(beanMethods.getConfigClazz());
        Method method = simpleMethodMetadata.get(0).getMethod();
        return method.invoke(bean, null);


    }

    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        this.beanFactory = simpleBeanFactory;
    }
}

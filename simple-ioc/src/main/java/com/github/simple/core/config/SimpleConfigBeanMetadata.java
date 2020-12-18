package com.github.simple.core.config;

import com.github.simple.core.annotation.SimpleBeanMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 2:47 下午
 * @description: SimpleConfigBeanMetadata
 */

public abstract class SimpleConfigBeanMetadata {

    protected List<SimpleBeanMethod> beanMethods=new ArrayList<>();

    protected SimpleBeanMethod getBeanMethodByMethodName(String methodBeanName) {
        return getBeanMethods().stream().filter(m -> m.getMethodMetadata().stream().anyMatch(n -> n.getMethodName().equals(methodBeanName))).collect(Collectors.toList()).get(0);
    }

    public List<SimpleBeanMethod> getBeanMethods() {
        return beanMethods;
    }

    public void setBeanMethods(SimpleBeanMethod beanMethod) {
        this.beanMethods.add(beanMethod);
    }

    public boolean matchMethodName(String name) {
        return beanMethods.stream().anyMatch(simpleBeanMethod -> simpleBeanMethod.getMethodMetadata().stream().anyMatch(s -> s.getMethodName().equals(name)));
    }

    public Method getConfigBeanMethod(String name) {
        return beanMethods.stream().filter(s -> s.getMethodMetadata().stream().anyMatch(t -> t.getMethodName().equals(name))).map(m -> m.getMethodMetadata().get(0).getMethod()).collect(Collectors.toList()).get(0);
    }

    public Boolean matchConfigClass(Class<?> clazz) {
        return getBeanMethods().stream().anyMatch(s -> s.getConfigClazz().equals(clazz));
    }

    public SimpleBeanMethod getBeanMethodByConfig(Class<?> configClass){
        return getBeanMethods().stream().filter(m->m.getConfigClazz().equals(configClass)).findFirst().orElse(null);
    }
}

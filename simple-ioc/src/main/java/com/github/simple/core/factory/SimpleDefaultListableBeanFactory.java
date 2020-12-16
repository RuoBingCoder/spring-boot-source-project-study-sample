package com.github.simple.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.utils.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:01 下午
 * @description: SimpleDefaultListableBeanFactory
 */

public class SimpleDefaultListableBeanFactory extends SimpleAutowiredCapableBeanFactory implements SimpleListableBeanFactory {
    public SimpleDefaultListableBeanFactory() throws Throwable {
        super();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) throws Throwable {
        return getBeansOfType(clazz, true);
    }

    private <T> Map<String, T> getBeansOfType(Class<T> clazz, boolean needInit) throws Throwable {
        return doGetBeans(clazz, needInit);
    }

    private <T> Map<String, T> doGetBeans(Class<T> clazz, boolean needInit) throws Throwable {
        Map<String, T> beans = new HashMap<>();
        if (needInit) {
            Map<String, SimpleRootBeanDefinition> beanDefinitionMap = super.getBeanDefinitions();
            Map<String, SimpleRootBeanDefinition> definitionTypeMap = beanDefinitionMap.entrySet().stream()
                    .filter(d -> clazz.isAssignableFrom(ClassUtils.getClass(d.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (CollectionUtil.isNotEmpty(definitionTypeMap)) {
                for (Map.Entry<String, SimpleRootBeanDefinition> entry : definitionTypeMap.entrySet()) {
                    if (this.getBean(entry.getKey()) == null) {
                        beans.put(entry.getKey(), (T) ClassUtils.newInstance(ClassUtils.getClass(entry.getValue())));
                    }
                }
            }
        }
        String[] names = matchType(clazz);
        if (names.length > 0) {
            for (String name : names) {
                beans.put(name, super.getBean(name));
            }
        }
        return beans;
    }

    private <T> String[] matchType(Class<T> clazz) {
        List<String> names = new ArrayList<>();
        getBeans().forEach((key, obj) -> {
            if (clazz.isAssignableFrom(obj.getClass())) {
                names.add(key);
            }

        });
        return StringUtils.toStringArray(names);
    }






    @Override
    public <T> T getBean(Class<?> clazz) throws Throwable {
        return super.getBean(clazz);
    }

    @Override
    public <T> T getBean(String name) throws Throwable {
        return super.getBean(name);
    }
}

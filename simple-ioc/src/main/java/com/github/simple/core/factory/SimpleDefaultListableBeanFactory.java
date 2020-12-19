package com.github.simple.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.SimpleAutowiredAnnotationBeanPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.beans.SimpleFactoryBean;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:01 下午
 * @description: SimpleDefaultListableBeanFactory
 */

public class SimpleDefaultListableBeanFactory extends SimpleAutowireCapableBeanFactory implements SimpleListableBeanFactory {
    /**
     * simpleFactoryBean cache
     */
    private static final Map<String, SimpleFactoryBean> factoryBeanCache = new ConcurrentHashMap<>();

    public SimpleDefaultListableBeanFactory(Class<?> startClass) throws Throwable {
        super(ReflectUtils.getBasePackages(startClass));

    }


    public static SimpleDefaultListableBeanFactory run(Class<?> clazz) throws Throwable {
        return new SimpleDefaultListableBeanFactory(clazz);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) throws Throwable {
        return getBeansOfType(clazz, true);
    }

    @Override
    public String[] getBeanNames() {
        Set<String> beanNamesSet = getBeans().keySet();
        return StringUtils.toStringArray(beanNamesSet);
    }

    private <T> Map<String, T> getBeansOfType(Class<T> clazz, boolean needInit) throws Throwable {
        return doGetBeans(clazz, needInit);
    }

    /**
     * 类型获取bean map
     *
     * @param clazz
     * @param needInit
     * @param <T>
     * @return
     * @throws Throwable 此处有bug //TODO
     */
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
    protected void predictBeanType(Object bean) {
        if (bean instanceof SimpleFactoryBean) {
            factoryBeanCache.put(ClassUtils.transformFactoryBeanName(bean.getClass()), (SimpleFactoryBean) bean);
        }
    }


    @Override
    protected Object getFactoryBeanInnerInstance(Object singleton, String beanName) {
        if (singleton instanceof SimpleFactoryBean) {
            SimpleFactoryBean sb = (SimpleFactoryBean) singleton;
            return sb.getObject();
        }
        return singleton;
    }

    @Override
    protected Object getFactoryObject(String name) {
        return factoryBeanCache.get(name);
    }

    @Override
    public <T> T getBean(String name) throws Throwable {
        return super.getBean(name);
    }

    @Override
    protected void processInjectionBasedOnCurrentContext(List<SimpleBeanPostProcessor> sortedPostProcessors) {
        for (SimpleBeanPostProcessor sortedPostProcessor : sortedPostProcessors) {
            if (sortedPostProcessor instanceof SimpleAutowiredAnnotationBeanPostProcessor) {
                SimpleAutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = (SimpleAutowiredAnnotationBeanPostProcessor) sortedPostProcessor;
                autowiredAnnotationBeanPostProcessor.setBeanFactory(this);
            }
        }
    }

    @Override
    protected void destroyFactoryBeanCache() {
        factoryBeanCache.clear();
    }


    @Override
    public <T> List<T> getBeanForType(Class<?> clazz, Class<?> type) throws Throwable {
        List<Object> factoryObjects = getBeans().values().stream().filter(o -> type.isAssignableFrom(o.getClass())).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(factoryObjects)) {
            return (List<T>) factoryObjects;
        }
        return Collections.emptyList();
    }

    @Override
    public void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor) {
        super.addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }
}

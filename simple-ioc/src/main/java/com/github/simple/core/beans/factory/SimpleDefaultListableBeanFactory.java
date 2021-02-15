package com.github.simple.core.beans.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.beans.SimpleFactoryBean;
import com.github.simple.core.beans.aware.SimpleBeanFactoryAware;
import com.github.simple.core.beans.factory.support.SimpleBeanDefinitionRegistry;
import com.github.simple.core.context.SimpleApplicationEvent;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.env.SimpleEnvironment;
import com.github.simple.core.env.SimpleMutablePropertySources;
import com.github.simple.core.env.SimpleStandardEnvironment;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.github.simple.core.utils.ReflectUtils.isValue;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:01 下午
 * @description: SimpleDefaultListableBeanFactory
 */
@Slf4j
public class SimpleDefaultListableBeanFactory extends SimpleAutowireCapableBeanFactory implements SimpleListableBeanFactory, SimpleBeanDefinitionRegistry {

    @Override
    public void publishEvent(SimpleApplicationEvent event) {

    }

    /**
     * simpleFactoryBean cache
     */
    private final Map<String, SimpleFactoryBean<?>> factoryBeanCache = new ConcurrentHashMap<>(256);

    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(256);

    private final List<SimpleStringValueResolver> simpleStringValueResolvers = new ArrayList<>(32);


    /**
     * 获取环境信息
     *
     * @return {@link Object }
     * @author jianlei.shi
     * @date 2021-02-14 22:09:44
     */
    @Override
    public SimpleEnvironment getEnvironment() {
        return (SimpleEnvironment) resolvableDependencies.get(SimpleStandardEnvironment.class);
    }

    public SimpleDefaultListableBeanFactory() {

    }

    public SimpleStringValueResolver getStringValueResolver() {
        if (CollectionUtil.isNotEmpty(simpleStringValueResolvers)) {
            return simpleStringValueResolvers.get(0);
        }
        throw new SimpleIOCBaseException("simpleStringValueResolvers is empty!");
    }

    @Override
    public String resolveEmbeddedValue(String value) throws Throwable {
        return (String) resolveStringValue(null, value);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) throws Throwable {
        return getBeansOfType(clazz, true);
    }

    @Override
    public String[] getBeanNames() {
        return beanDefinitionNames.toArray(new String[0]);
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
    public void processInjectionBasedOnCurrentContext(List<SimpleBeanPostProcessor> sortedPostProcessors) {
        for (SimpleBeanPostProcessor sortedPostProcessor : sortedPostProcessors) {
            if (sortedPostProcessor instanceof SimpleBeanFactoryAware) {
                SimpleBeanFactoryAware beanFactory = (SimpleBeanFactoryAware) sortedPostProcessor;
                beanFactory.setBeanFactory(this);
            }
        }
    }

    @Override
    protected void destroyFactoryBeanCache() {
        factoryBeanCache.clear();
    }


    @Override
    public <T> List<T> getBeanForType(Class<?> clazz, Class<?> type) {
        List<Object> factoryObjects = getBeans().values().stream().filter(o -> type.isAssignableFrom(o.getClass())).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(factoryObjects)) {
            return (List<T>) factoryObjects;
        }
        return Collections.emptyList();
    }

    /**
     * 解决字符串值
     *
     * @param type        类型
     * @param placeHolder 占位符
     * @return {@link Object}
     */
    public Object resolveStringValue(Field type, String placeHolder) {
        if (log.isDebugEnabled()) {
            log.debug("====>>>>@SimpleValue set value begin<<<<<======");
        }
        boolean flag = false;
        SimpleStandardEnvironment environment = (SimpleStandardEnvironment) this.getEnvironment();
        SimpleMutablePropertySources propertySources = environment.getPropertySources();
        List<SimplePropertySource<?>> simplePropertySources = propertySources.getSimplePropertySources();
        AtomicInteger ai = new AtomicInteger(0);
            if (CollectionUtil.isNotEmpty(simplePropertySources)) {
                for (SimplePropertySource<?> propertySource : simplePropertySources) {
                    if (!(propertySource.getValue() instanceof Properties)) {
                        flag = true;
                    }
                    if (type != null && placeHolder == null) {
                        String key = com.github.simple.core.utils.StringUtils.parsePlaceholder(type);
                        Object value = findValue(propertySource, key, flag);
                        ai.getAndIncrement();
                        if (value == null && ai.get() == simplePropertySources.size()) {
                            throw new SimpleIOCBaseException("no such field placeholder->${" + key + "}");
                        }
                        if (value != null) {
                            return TypeConvertUtils.convert(type.getType(), (String) value);
                        }
                    }
                    //现在用不到
                    if (placeHolder != null) {
                        ai.getAndIncrement();
                        String key = com.github.simple.core.utils.StringUtils.resolvePlaceholder(placeHolder);
                        final Object value = findValue(propertySource, key, flag);
                        if (value == null && ai.get() == simplePropertySources.size()) {
                            throw new SimpleIOCBaseException("no such field placeholder->${" + key + "}");
                        }
                        if (value != null) {
                            return value;
                        }

                    }
                }
            }
        return null;
    }


    /**
     * 解析非容器管理bean和管理bean
     *
     * @param beanName
     * @return
     * @throws Throwable
     */
    @Override
    public Object resolveDependency(Field type, String beanName) throws Throwable {
        log.info("==> 解析字段name:{},字段类型:{}", beanName, type.getType());
        //接口
        if (isValue(type)) {
            return resolveStringValue(type, null);
        }
        //#1 修复非spring bean String类型注入错误
        if (resolvableDependencies.containsKey(type.getType())) {
            return resolvableDependencies.get(type.getType());
        }
        //String Integer 类型自动注入

        return getBean(beanName);
    }


    @Override
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }

    @Override
    public void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue) {
        resolvableDependencies.put(dependencyType, autowiredValue);
    }


    private <T> Object findValue(T resource, String key, boolean isYaml) {
        if (!isYaml) {
            SimplePropertySource<Properties> propertySource = (SimplePropertySource<Properties>) resource;
            return propertySource.getValue().get(key);
        }
        SimplePropertySource<Map<String, Object>> propertySource = (SimplePropertySource<Map<String, Object>>) resource;
        return YamlUtils.getPropertyBySource(key, propertySource);
    }


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        super.addSingleton(beanName, singletonObject);
    }

    public List<SimpleBeanFactoryPostProcessor> getFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    public List<SimpleBeanPostProcessor> getBeanPostProcessors() {
        return simplePostProcessors;
    }

    @Override
    public void addBeanFactoryPostProcessor(SimpleBeanFactoryPostProcessor beanFactoryPostProcessor) {
        getFactoryPostProcessors().add(beanFactoryPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(SimpleStringValueResolver valueResolver) {
        simpleStringValueResolvers.add(valueResolver);
    }

    @Override
    public void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor) {
        getBeanPostProcessors().add(beanPostProcessor);
    }


    @Override
    public void registerBeanDefinition(String beanName, SimpleRootBeanDefinition beanDefinition) {
        super.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        beanDefinitions.remove(beanName);
    }

    @Override
    public SimpleRootBeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        return beanDefinitions.get(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionNames.toArray(new String[0]);
    }


}

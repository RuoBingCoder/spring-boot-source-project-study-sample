package com.github.simple.core.beans.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.annotation.SimpleInstantiationAwareBeanPostProcessor;
import com.github.simple.core.beans.SimpleFactoryBean;
import com.github.simple.core.beans.aware.SimpleBeanFactoryAware;
import com.github.simple.core.config.SimpleConfigBean;
import com.github.simple.core.context.SimpleApplicationContext;
import com.github.simple.core.context.aware.SimpleApplicationContextAware;
import com.github.simple.core.context.aware.SimpleEmbeddedValueResolverAware;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.init.SimpleInitializingBean;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/12/11 6:32 下午
 * @description: 能力bean工厂
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 */
@Slf4j
public abstract class SimpleAutowireCapableBeanFactory extends AbsBeanFactory {

    protected List<SimplePropertySource<Properties>> simplePropertiesPropertySourceLoader;
    protected List<SimplePropertySource<Map<String, Object>>> simpleYamlPropertySourceLoader;
    protected SimpleApplicationContext applicationContext;

    /**
     * 增加资源配置文件
     *
     * @param source 源
     */
    @Override
    public <T> void addPropertySource(T source) {
        List<SimplePropertySource> list = (List<SimplePropertySource>) source;
        if (!(list.get(0).getValue() instanceof Properties)) {
            this.simpleYamlPropertySourceLoader = (List<SimplePropertySource<Map<String, Object>>>) source;
            return;
        }
        this.simplePropertiesPropertySourceLoader = (List<SimplePropertySource<Properties>>) source;
    }


    /**
     * 设置应用程序上下文
     *
     * @param simpleApplicationContext 简单的应用程序上下文
     */
    @Override
    public void setApplicationContext(SimpleApplicationContext simpleApplicationContext) {
        this.applicationContext = simpleApplicationContext;
    }

    /**
     * 完成bean实例
     *
     * @throws Throwable throwable
     */
    public void finishBeanInstance() throws Throwable {
        List<String> beanNames = this.beanDefinitionNames;
        for (String beanName : beanNames) {
            if (isFactoryBean(beanName)) {
                getBean(beanName);
            }
        }
        for (String beanName : beanNames) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("-->getBean beanName is:{}", beanName);
                }
                this.getBean(beanName);
            } catch (Exception e) {
                log.error("getBean exception!", e);
                throw new SimpleIOCBaseException("finishBeanInstance getBean exception! beanName is :[" + beanName + "]" + e.getMessage());
            }
        }


    }


    /**
     * 是工厂bean
     *
     * @param beanName bean的名字
     * @return boolean
     */
    private boolean isFactoryBean(String beanName) {
        return beanDefinitions.entrySet().stream().filter(s -> s.getKey().equals(beanName)).anyMatch(m -> SimpleFactoryBean.class.isAssignableFrom(m.getValue().getBeanClass()));
    }


    /**
     * 注册表的bean定义
     *
     * @param basePackages 基本包
     */
    public void registryBeanDefinition(String basePackages) {
        Set<Class<?>> classSet = ClassUtils.scannerBasePackages(basePackages);
        doRegistryBeanDefinition(classSet);
    }

    @Override
    protected Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable {
        return doCreateBean(beanName);
    }

    /**
     * 做创建bean
     *
     * @param beanName bean的名字
     * @return {@link Object}
     * @throws Throwable throwable
     */
    private Object doCreateBean(String beanName) throws Throwable {
        //创建bean
        //填充属性
        //初始化
        SimpleRootBeanDefinition simpleRootBeanDefinition = beanDefinitions.get(beanName);
        Object bean = invokerBeforeInstantiation(simpleRootBeanDefinition.getBeanClass(), simpleRootBeanDefinition.getBeanName());
        if (ObjectUtil.isNotNull(bean)) {
            return bean;
        }

        Object instance = null;
        if (simpleRootBeanDefinition.getIsSingleton()) {
            instance = createInstance(simpleRootBeanDefinition);
        }
        //添加aop代理bean
        Object exportObject = instance;
        addSingletonFactory(beanName, () -> earlyRefSingleton(beanName, exportObject));
        //填充属性
        populateBean(beanName, exportObject);
        //初始化bean
        return initialization(beanName, exportObject);
    }

    /**
     * 调用程序实例化之前
     *
     * @param rootClass 根类
     * @param beanName  bean的名字
     * @return {@link Object}
     */
    private Object invokerBeforeInstantiation(Class<?> rootClass, String beanName) {
        if (CollectionUtil.isEmpty(getBeanPostProcessor())) {
            return null;
        }
        for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
            if (simplePostProcessor instanceof SimpleInstantiationAwareBeanPostProcessor) {
                SimpleInstantiationAwareBeanPostProcessor sip = (SimpleInstantiationAwareBeanPostProcessor) simplePostProcessor;
                Object bean = sip.postProcessBeforeInstantiation(rootClass, beanName);
                if (bean != null) {
                    return bean;
                }
            }
        }
        return null;

    }

    /**
     * 初始化
     *
     * @param beanName bean的名字
     * @param instance 实例
     * @return {@link Object}
     * @throws Throwable throwable
     */
    private Object initialization(String beanName, Object instance) throws Throwable {
        invokerAware(beanName, instance);
        invokerBeforeInitialization(beanName, instance);
        try {
            initMethods(beanName, instance);
        } catch (Exception e) {
            log.error("invoker initMethods error!", e);
            throw new SimpleIOCBaseException("invoker initMethods error");
        }
        Object shareObject = invokerAfterInitialization(beanName, instance);
        if (shareObject != null) {
            return shareObject;
        }
        return instance;


    }

    /**
     * 调用程序初始化后
     *
     * @param beanName bean的名字
     * @param instance 实例
     * @return {@link Object}
     * @throws Throwable throwable
     */
    private Object invokerAfterInitialization(String beanName, Object instance) throws Throwable {
        if (CollectionUtil.isEmpty(getBeanPostProcessor())) {
            return instance;
        }
        for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
            Object bean = simplePostProcessor.postProcessAfterInitialization(instance, beanName);
            if (bean != null) {
                return bean;
            }
        }
        return instance;
    }

    /**
     * 调用程序之前初始化
     *
     * @param beanName bean的名字
     * @param instance 实例
     */
    private void invokerBeforeInitialization(String beanName, Object instance) {
        if (CollectionUtil.isEmpty(getBeanPostProcessor())) {
            return;
        }
        for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
            Object bean = simplePostProcessor.postProcessBeforeInitialization(instance, beanName);
            if (ObjectUtil.isNotNull(bean)) {
                return;
            }
        }
    }

    /**
     * init方法
     *
     * @param beanName bean的名字
     * @param bean     豆
     */
    private void initMethods(String beanName, Object bean) {
        if (bean instanceof SimpleInitializingBean) {
            SimpleInitializingBean simpleInitializingBean = (SimpleInitializingBean) bean;
            simpleInitializingBean.afterPropertiesSet();

        }
    }

    private void invokerAware(String beanName, Object instance) {
        if (instance instanceof SimpleBeanFactoryAware) {
            SimpleBeanFactoryAware simpleBeanFactoryAware = (SimpleBeanFactoryAware) instance;
            simpleBeanFactoryAware.setBeanFactory(this);

        }
        if (instance instanceof SimpleEmbeddedValueResolverAware) {
            SimpleEmbeddedValueResolverAware evr = (SimpleEmbeddedValueResolverAware) instance;
            SimpleDefaultListableBeanFactory beanFactory = (SimpleDefaultListableBeanFactory) this;
            evr.setEmbeddedValueResolver(beanFactory.getStringValueResolver());
        }
        if (instance instanceof SimpleApplicationContextAware) {
            SimpleApplicationContextAware simpleApplicationContext = (SimpleApplicationContextAware) instance;
            simpleApplicationContext.setApplicationContext(this.applicationContext);
        }
    }

    /**
     * 属性填充
     *
     * @param beanName
     * @param instance
     * @throws Throwable
     */
    private void populateBean(String beanName, Object instance) throws Throwable {
        if (CollectionUtil.isNotEmpty(getBeanPostProcessor())) {
            for (SimpleBeanPostProcessor simplePostProcessor : getBeanPostProcessor()) {
                if (simplePostProcessor instanceof SimpleInstantiationAwareBeanPostProcessor) {
                    SimpleInstantiationAwareBeanPostProcessor sbp = (SimpleInstantiationAwareBeanPostProcessor) simplePostProcessor;
                    if (!sbp.postProcessAfterInstantiation(instance, beanName)) {
                        return;
                    }
                    sbp.postProcessProperties(instance, beanName);
                }
            }

        }
    }


    private Object createInstance(SimpleRootBeanDefinition mbd) throws InstantiationException, IllegalAccessException {
        Object bean = handlerIsConfigBean(mbd);
        if (bean != null) {
            return bean;
        }
        return ClassUtils.newInstance(mbd.getBeanClass());

    }

    private Object handlerIsConfigBean(SimpleRootBeanDefinition mbd) {
        try {
            if (mbd.getBeanClass().equals(SimpleConfigBean.class)) {
                return null;
            }
            SimpleConfigBean configBean = getBean(SimpleConfigBean.class);
            if (configBean == null) {
                throw new SimpleIOCBaseException("get config bean exception!");
            }
            if (configBean.matchMethodName(mbd.getBeanName())) {
                return configBean.invoker(mbd.getBeanName());
            }
        } catch (Throwable e) {
            throw new SimpleIOCBaseException("handler configBean exception!" + e.getMessage());
        }
        return null;
    }

    public void addSingletonFactory(String beanName, SimpleObjectFactory<?> factory) {
        synchronized (singletonFactoryMap) {
            singletonFactoryMap.put(beanName, factory);
        }

    }


    public Map<String, Object> getBeans() {
        return singletonObjectMap;
    }

    @Override
    public <T> T getResource() {
        if (simplePropertiesPropertySourceLoader != null) {
            return (T) simplePropertiesPropertySourceLoader;
        }
        if (simpleYamlPropertySourceLoader != null) {
            return (T) simpleYamlPropertySourceLoader;
        }
        throw new SimpleIOCBaseException("Error message is: 【 Resource is null! 】");
    }
}

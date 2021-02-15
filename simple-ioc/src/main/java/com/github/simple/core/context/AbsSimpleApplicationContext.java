package com.github.simple.core.context;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.SimpleBeanDefinitionRegistryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.aop.SimpleAutoProxyCreator;
import com.github.simple.core.beans.factory.SimpleAutowireCapableBeanFactory;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleConfigBeanFactory;
import com.github.simple.core.beans.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.core.beans.factory.config.SimpleEmbeddedValueResolver;
import com.github.simple.core.beans.factory.support.SimpleBeanDefinitionRegistry;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.context.event.SimpleApplicationEventMulticaster;
import com.github.simple.core.context.event.StandardSimpleApplicationEventMulticaster;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.env.SimpleEnvironmentPostProcessor;
import com.github.simple.core.env.SimpleMutablePropertySources;
import com.github.simple.core.env.SimpleStandardEnvironment;
import com.github.simple.core.resource.SimpleClassPathResource;
import com.github.simple.core.resource.SimplePropertiesPropertySourceLoader;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.resource.SimpleYamlPropertySourceLoader;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;
import com.github.simple.core.utils.SimpleStringValueResolver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 3:14 下午
 * @description: SimpleApplicationContext
 */
@Slf4j
public abstract class AbsSimpleApplicationContext implements SimpleConfigApplicationContext {

    private final Class<?> sourceClass;

    protected SimpleDefaultListableBeanFactory beanFactory;

    protected SimpleApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void addEmbeddedValueResolver(SimpleStringValueResolver valueResolver) {
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    @Override
    public void addApplicationListener(SimpleApplicationListener<?> listener) {
        this.applicationEventMulticaster.addApplicationListener(listener);
    }

    protected AbsSimpleApplicationContext(Class<?> sourceClass) throws Throwable {
        this.sourceClass = sourceClass;
        refresh();
    }

    @Override
    public SimpleBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void refresh() throws Throwable {
        SimpleConfigBeanFactory beanFactory = obtainFreshBeanFactory();
        prepareBeanFactory(beanFactory);
        preparePropertiesSource(beanFactory);
        prepareEnvironmentSource(beanFactory);
        try {
            postBeanFactory(beanFactory);
            invokerBeanFactoryPostProcessor(beanFactory);
            registryBeanPostProcessor(beanFactory);
            initApplicationEventMulticaster();
            finishBeanInstance(beanFactory);
            finishRefresh();
        } catch (Exception e) {
            log.error("ioc create exception", e);
            destroyBeans(beanFactory);
        }

    }

    protected void prepareEnvironmentSource(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory = (SimpleDefaultListableBeanFactory) beanFactory;
        final SimpleStandardEnvironment simpleStandardEnvironment = new SimpleStandardEnvironment();
        simpleStandardEnvironment.setBeanFactory(beanFactory);
        defaultListableBeanFactory.registerResolvableDependency(SimpleStandardEnvironment.class, simpleStandardEnvironment);
    }

    protected abstract void finishRefresh() throws Throwable;

    @Override
    public void publishEvent(SimpleApplicationEvent event) throws Throwable {
        this.applicationEventMulticaster.multicastEvent(event);
    }

    private void initApplicationEventMulticaster() {
        this.applicationEventMulticaster = new StandardSimpleApplicationEventMulticaster(this);

    }

    private SimpleConfigBeanFactory obtainFreshBeanFactory() {
        SimpleDefaultListableBeanFactory beanFactory = new SimpleDefaultListableBeanFactory();
        beanFactory.registryBeanDefinition(ReflectUtils.getBasePackages(sourceClass));
        this.beanFactory = beanFactory;
        beanFactory.setApplicationContext((SimpleApplicationContext) this);
        return beanFactory;

    }

    private void destroyBeans(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory factory = (SimpleDefaultListableBeanFactory) beanFactory;
        factory.destroyBeans();

    }

    private void finishBeanInstance(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory = (SimpleDefaultListableBeanFactory) beanFactory;
        factory.finishBeanInstance();

    }

    private void registryBeanPostProcessor(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory = (SimpleDefaultListableBeanFactory) beanFactory;
        List<SimpleBeanPostProcessor> sortedPostProcessors = new ArrayList<>();
        List<SimpleBeanPostProcessor> nonOrderPostprocessors = new ArrayList<>();
        List<SimpleEnvironmentPostProcessor> environmentPostProcessors=new ArrayList<>();
        SimpleAutoProxyCreator spc = new SimpleAutoProxyCreator();
        sortedPostProcessors.add(spc);
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : factory.getBeanDefinitions().entrySet()) {
            if (SimpleBeanPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanPostProcessor simplePostProcessor = factory.getBean(entry.getKey());
                nonOrderPostprocessors.add(simplePostProcessor);
            }
            if (SimpleEnvironmentPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))){
                SimpleEnvironmentPostProcessor envPr=factory.getBean(entry.getKey());
                environmentPostProcessors.add(envPr);
            }

        }
        factory.doInvokerEnvPostProcessors(environmentPostProcessors);
        factory.sortPostProcessors(nonOrderPostprocessors, sortedPostProcessors);
        factory.processInjectionBasedOnCurrentContext(sortedPostProcessors);
        factory.getBeanPostProcessors().addAll(sortedPostProcessors);
    }

    private void invokerBeanFactoryPostProcessor(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory = (SimpleDefaultListableBeanFactory) beanFactory;
        if (CollectionUtil.isNotEmpty(defaultListableBeanFactory.getFactoryPostProcessors())) {
            for (SimpleBeanFactoryPostProcessor postProcessor : defaultListableBeanFactory.getFactoryPostProcessors()) {
                if (postProcessor instanceof SimpleBeanDefinitionRegistryPostProcessor) {
                    SimpleBeanDefinitionRegistryPostProcessor registryPostProcessor = (SimpleBeanDefinitionRegistryPostProcessor) postProcessor;
                    registryPostProcessor.postProcessBeanDefinitionRegistry((SimpleBeanDefinitionRegistry) beanFactory);
                    continue;
                }
                postProcessor.postProcessBeanFactory(defaultListableBeanFactory);
            }
        }
    }

    private void postBeanFactory(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory = (SimpleDefaultListableBeanFactory) beanFactory;
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : factory.getBeanDefinitions().entrySet()) {
            if (SimpleBeanFactoryPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanFactoryPostProcessor postFactoryPostProcessor = factory.getBean(entry.getKey());
                factory.addBeanFactoryPostProcessor(postFactoryPostProcessor);
            }
        }

    }

    private void preparePropertiesSource(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory = (SimpleDefaultListableBeanFactory) beanFactory;
        SimpleClassPathResource source = new SimpleClassPathResource(SimpleIOCConstant.DEFAULT_SOURCE_NAME);
        final SimpleMutablePropertySources mps = beanFactory.getBean(SimpleMutablePropertySources.class);
        // yaml
        if (source.fileAttribute()) {
            SimpleYamlPropertySourceLoader loader = new SimpleYamlPropertySourceLoader();
            List<SimplePropertySource<Map<String, Object>>> propertySourceList = loader.load(source.getFilename(), source);
            mps.addLast(propertySourceList.get(0));
//            defaultListableBeanFactory.addPropertySource(propertySourceList);
        } else {
            // properties
            SimplePropertiesPropertySourceLoader loader = new SimplePropertiesPropertySourceLoader();
            List<SimplePropertySource<Properties>> psp = loader.load(source.getFilename(), source);
            mps.addLast(psp.get(0));

//            defaultListableBeanFactory.addPropertySource(simplePropertiesPropertySourceLoader);
        }
        defaultListableBeanFactory.addEmbeddedValueResolver(new SimpleEmbeddedValueResolver(defaultListableBeanFactory));

    }


    private void prepareBeanFactory(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory = (SimpleDefaultListableBeanFactory) beanFactory;
        defaultListableBeanFactory.registerResolvableDependency(SimpleBeanFactory.class, beanFactory);
        defaultListableBeanFactory.registerResolvableDependency(SimpleAutowireCapableBeanFactory.class, beanFactory);
        defaultListableBeanFactory.registerResolvableDependency(SimpleDefaultListableBeanFactory.class, beanFactory);
    }
}

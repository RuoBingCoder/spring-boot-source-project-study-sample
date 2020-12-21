package com.github.simple.core.context;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.annotation.SimpleBeanFactoryPostProcessor;
import com.github.simple.core.annotation.SimpleBeanPostProcessor;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.factory.SimpleAutowireCapableBeanFactory;
import com.github.simple.core.factory.SimpleBeanFactory;
import com.github.simple.core.factory.SimpleConfigBeanFactory;
import com.github.simple.core.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.core.resource.SimpleClassPathResource;
import com.github.simple.core.resource.SimplePropertiesPropertySourceLoader;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;
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

    protected SimpleBeanFactory beanFactory;


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
        SimpleConfigBeanFactory beanFactory=obtainFreshBeanFactory();
        prepareBeanFactory(beanFactory);
        preparePropertiesSource(beanFactory);
        try {
            postBeanFactory(beanFactory);
            invokerBeanFactoryPostProcessor(beanFactory);
            invokerBeanPostProcessor(beanFactory);
            finishBeanInstance(beanFactory);
        } catch (Exception e) {
            log.error("ioc create exception", e);
            destroyBeans(beanFactory);
        }

    }

    private SimpleConfigBeanFactory obtainFreshBeanFactory() {
        SimpleDefaultListableBeanFactory beanFactory=new SimpleDefaultListableBeanFactory();
        beanFactory.registryBeanDefinition(ReflectUtils.getBasePackages(sourceClass));
        this.beanFactory=beanFactory;
        return beanFactory;

    }

    private void destroyBeans(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory factory= (SimpleDefaultListableBeanFactory) beanFactory;
        factory.destroyBeans();

    }

    private void finishBeanInstance(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory= (SimpleDefaultListableBeanFactory) beanFactory;
        factory.finishBeanInstance();

    }

    private void invokerBeanPostProcessor(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory= (SimpleDefaultListableBeanFactory) beanFactory;
        List<SimpleBeanPostProcessor> sortedPostProcessors = new ArrayList<>();
        List<SimpleBeanPostProcessor> nonOrderPostprocessors = new ArrayList<>();
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : factory.getBeanDefinitions().entrySet()) {
            if (SimpleBeanPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanPostProcessor simplePostProcessor = factory.getBean(entry.getKey());
                nonOrderPostprocessors.add(simplePostProcessor);
            }

        }
        factory.sortPostProcessors(nonOrderPostprocessors, sortedPostProcessors);
        factory.processInjectionBasedOnCurrentContext(sortedPostProcessors);
        factory.getBeanPostProcessors().addAll(sortedPostProcessors);
    }

    private void invokerBeanFactoryPostProcessor(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory= (SimpleDefaultListableBeanFactory) beanFactory;
        if (CollectionUtil.isNotEmpty(defaultListableBeanFactory.getFactoryPostProcessors())) {
                for (SimpleBeanFactoryPostProcessor postProcessor : defaultListableBeanFactory.getFactoryPostProcessors()) {
                    postProcessor.postProcessBeanFactory(defaultListableBeanFactory);
                }
        }
    }

    private void postBeanFactory(SimpleConfigBeanFactory beanFactory) throws Throwable {
        SimpleDefaultListableBeanFactory factory= (SimpleDefaultListableBeanFactory) beanFactory;
        for (Map.Entry<String, SimpleRootBeanDefinition> entry : factory.getBeanDefinitions().entrySet()) {
            if (SimpleBeanFactoryPostProcessor.class.isAssignableFrom(ClassUtils.getClass(entry.getValue()))) {
                SimpleBeanFactoryPostProcessor postFactoryPostProcessor = factory.getBean(entry.getKey());
                factory.addBeanFactoryPostProcessor(postFactoryPostProcessor);
            }
        }

    }

    private void preparePropertiesSource(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory= (SimpleDefaultListableBeanFactory) beanFactory;
        SimpleClassPathResource source = new SimpleClassPathResource(SimpleIOCConstant.DEFAULT_SOURCE_NAME);
        SimplePropertiesPropertySourceLoader loader = new SimplePropertiesPropertySourceLoader();
        List<SimplePropertySource<Properties>> simplePropertiesPropertySourceLoader = loader.load(source.getFilename(), source);
        defaultListableBeanFactory.setSimplePropertiesPropertySourceLoader(simplePropertiesPropertySourceLoader);

    }

    private void prepareBeanFactory(SimpleConfigBeanFactory beanFactory) {
        SimpleDefaultListableBeanFactory defaultListableBeanFactory= (SimpleDefaultListableBeanFactory) beanFactory;
        defaultListableBeanFactory.registerResolvableDependency(SimpleBeanFactory.class,beanFactory);
        defaultListableBeanFactory.registerResolvableDependency(SimpleAutowireCapableBeanFactory.class,beanFactory);
        defaultListableBeanFactory.registerResolvableDependency(SimpleDefaultListableBeanFactory.class,beanFactory);
    }
}

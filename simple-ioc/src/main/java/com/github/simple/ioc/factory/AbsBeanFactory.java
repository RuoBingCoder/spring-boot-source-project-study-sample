package com.github.simple.ioc.factory;

import com.github.simple.ioc.annotation.SimplePostProcessor;
import com.github.simple.ioc.definition.SimpleRootBeanDefinition;
import com.github.simple.ioc.enums.SimpleIOCEnum;
import com.github.simple.ioc.exception.SimpleBeanCreateException;
import com.github.simple.ioc.exception.SimpleClassNotFoundException;
import com.github.simple.ioc.utils.ClassUtils;
import com.github.simple.ioc.utils.IOCReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:29 下午
 * @description: AbsBeanFactory
 */

public abstract class AbsBeanFactory extends SimpleDefaultSingletonBeanRegistry implements SimpleBeanFactory {

    /**
     * bean 定义
     */
    protected final Map<String, SimpleRootBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * bean后置处理器
     */
    protected List<SimplePostProcessor> simplePostProcessors;

    @Override
    public Object getBean(Class<?> clazz) throws Throwable {
        return doGetBean(clazz.getSimpleName());
    }

    /**
     * 获取bean
     *
     * @param name
     * @return
     * @throws Throwable
     */
    protected Object doGetBean(String name) throws Throwable {
        String beanName = transformName(name);
        Object singleton = getSingleton(beanName);

        if (singleton != null) {
            return singleton;
        }
        SimpleRootBeanDefinition sbf = getMergeBeanDefinition(beanName);

        return getSingletonBean(beanName, () ->
                {
                    try {
                        return createBean(beanName, sbf);
                    } catch (Exception e) {
                        throw new SimpleBeanCreateException(SimpleIOCEnum.CREATE_BEAN_ERROR.getMsg());

                    }
                }
        );
    }

    private SimpleRootBeanDefinition getMergeBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    protected abstract Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable;


    private String transformName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new SimpleClassNotFoundException(SimpleIOCEnum.CLASS_TYPE_NOT_NULL.getMsg());
        }
        if (name.length() >= 1 && name.toCharArray()[0] >= 97 && name.toCharArray()[0] <= 122) {
            return name;
        }
        return ClassUtils.toLowerBeanName(name);
    }

    @Override
    public Object getBean(String name) throws Throwable {
        return doGetBean(name);
    }

    @Override
    public Map<String, Object> getBeanOfType(Class<?> clazz) {
        return null;
    }


    public void setBeanDefinitionMap(String beanName, SimpleRootBeanDefinition rootBeanDefinition) {
        if (!this.beanDefinitionMap.containsKey(beanName)) {
            synchronized (this.beanDefinitionMap) {
                this.beanDefinitionMap.put(beanName, rootBeanDefinition);
            }
        }
//        System.out.println("=====>>>beanDefinitionMap size:" + beanDefinitionMap.size());
    }

    /**
     * @param wrapperInstance
     * @return obj 代理对象&本对象
     */
    protected Object earlySingleton(Object wrapperInstance) {
        //spring 此处为依赖注入检查bean是否为代理类,如果是则替换为代理类实现依赖注入
        return wrapperInstance;
    }

    public void doSetBeanDefinition(Set<Class<?>> classSet) {
        classSet.forEach(c -> {
            if (IOCReflectionUtils.matchComponent(c)) {
                SimpleRootBeanDefinition rootBeanDefinition = getRootBeanDefinition(c);
                setBeanDefinitionMap(rootBeanDefinition.getBeanName(), rootBeanDefinition);
            }
        });
    }

    private SimpleRootBeanDefinition getRootBeanDefinition(Class<?> clazz) {
        return SimpleRootBeanDefinition.builder().rootClass(clazz)
                .beanName(ClassUtils.toLowerBeanName(clazz.getSimpleName()))
                .isSingleton(true).build();
    }

    protected List<SimplePostProcessor> getBeanPostProcessor() {
        return this.simplePostProcessors;
    }

}

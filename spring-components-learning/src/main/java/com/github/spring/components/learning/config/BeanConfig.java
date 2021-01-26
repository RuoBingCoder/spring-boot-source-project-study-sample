package com.github.spring.components.learning.config;


import com.github.spring.components.learning.condition.SimpleCondition;
import com.github.spring.components.learning.pojo.AnnotationBean;
import com.github.spring.components.learning.pojo.ConfigBeanA;
import com.github.spring.components.learning.pojo.ConfigBeanB;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/12/4 下午6:46
 * @description: BeanConfig
 * @see ConfigurationClassPostProcessor#enhanceConfigurationClasses(ConfigurableListableBeanFactory) 加了此注解 {@link Configuration} BeanConfig 会被代理
 * @see ConfigurationClassPostProcessor#processConfigBeanDefinitions
 * @see ConfigurationClassParser#parse(Set)
 * @see ConfigurationClassParser#parse(AnnotationMetadata, String) 
 * @see ConfigurationClassParser#processConfigurationClass
 * @see ConfigurationClassParser#doProcessConfigurationClass
 * @see ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod
 * @see ConditionEvaluator#shouldSkip 条件注解 {@link org.springframework.boot.autoconfigure.condition.ConditionalOnBean}
 * @see org.springframework.boot.autoconfigure.condition.SpringBootCondition#matches
 * @see org.springframework.boot.autoconfigure.condition.OnBeanCondition#getMatchOutcome
 * @see org.springframework.boot.autoconfigure.condition.OnBeanCondition#getBeanNamesForType
 *
 */
@Configuration
public class BeanConfig {

    @Bean
    public AnnotationBean annotationBean(){
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setName("haha");
        return annotationBean;
    }



    @Bean
    @Primary
    public String hello(){
        return "hello word!";


    }


    @Bean
    public String message(){
        return "Messages";


    }

    @Bean
    public ConfigBeanA configBeanA(){
        return new ConfigBeanA();
    }

    /**
     * 配置beanb
     *如果此时B 里面调用A 不加 {@link Configuration} 则会违背spring 单例原则 ,加上 则会创建代理 {@link org.springframework.beans.factory.support.SimpleInstantiationStrategy#instantiate(RootBeanDefinition, String, BeanFactory, Object, Method, Object...)}
     *
     * method.invoke() 拦截器 {@link ConfigurationClassEnhancer.BeanMethodInterceptor#intercept(Object, Method, Object[], MethodProxy)} 会对方法调用顺序进行判断 保证已经创建过的bean 不会再次被创建
     * 解释: 因为BeanConfig 此时传进去的是代理类,因此invoke() configBeanB() 后->invoke() configBeanA() 也会走 intercept()方法 所以此时会进行判断ConfigBeanA 是否已经被创建
     *
     * @return {@link ConfigBeanB}
     * 
     * 
     * @see Test 演示了原理
     */
    @Bean
    @Conditional(SimpleCondition.class)
    public ConfigBeanB configBeanB(){
         configBeanA();
        return new ConfigBeanB();
    }

}

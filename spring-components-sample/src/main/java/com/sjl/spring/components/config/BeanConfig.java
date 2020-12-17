package com.sjl.spring.components.config;

import com.sjl.spring.components.pojo.AnnotationBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Primary;

/**
 * @author: JianLei
 * @date: 2020/12/4 下午6:46
 * @description: BeanConfig
 * @see ConfigurationClassPostProcessor#processConfigBeanDefinitions(BeanDefinitionRegistry)
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod
 * @see org.springframework.context.annotation.ConditionEvaluator#shouldSkip
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
}

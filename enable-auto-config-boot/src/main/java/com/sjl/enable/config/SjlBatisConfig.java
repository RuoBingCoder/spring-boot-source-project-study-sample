package com.sjl.enable.config;

import com.sjl.enable.scan.SjlBtaisScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SjlBatisConfig
 */
@Slf4j
public class SjlBatisConfig implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;
    private String beanName;
    private Class<? extends Annotation> annotationClass;
    private BeanNameGenerator nameGenerator;
    private ApplicationContext applicationContext;


    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        SjlBtaisScanner scanner=new SjlBtaisScanner(registry);
       // scanner.setAnnotationClass(annotationClass);
        scanner.registerFilters();
        final Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(this.basePackage);
        final Iterator<BeanDefinition> iterator = candidateComponents.iterator();
          if (iterator.hasNext()){
                log.info("=====>>>>:{}",iterator.next().getBeanClassName());
          }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("======================SjlBatisConfig init finish!===========================");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
    }
}

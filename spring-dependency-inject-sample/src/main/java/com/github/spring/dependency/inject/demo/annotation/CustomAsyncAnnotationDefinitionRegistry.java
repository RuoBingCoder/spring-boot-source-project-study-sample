package com.github.spring.dependency.inject.demo.annotation;

import com.github.spring.dependency.inject.demo.proxy.ProxyFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: JianLei
 * @date: 2020/11/28 下午1:56
 * @description: 自定义异步任务注册器;
 * @see CustomAsyncAnnotationPostProcessor 注册异步后置处理器只是为了仿照spring原理走的,原本可以@Compont注入
 */

public class CustomAsyncAnnotationDefinitionRegistry implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(EnableCustomAsync.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(annotationMetadata));
        }

    }


    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata) {
        return importingClassMetadata.getClassName() + "#" + CustomAsyncAnnotationDefinitionRegistry.class.getSimpleName() + "#" + 0;
    }

    private void registerBeanDefinitions(AnnotationAttributes attributes, BeanDefinitionRegistry registry, String beanName) {
        ProxyFactory.proxy = attributes.getString("value");
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(CustomAsyncAnnotationPostProcessor.class).addDependsOn("reflectiveUtil").getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);


    }

}

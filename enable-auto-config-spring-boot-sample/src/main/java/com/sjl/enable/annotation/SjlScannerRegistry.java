package com.sjl.enable.annotation;

import com.sjl.enable.scan.SjlExploreScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SjlPostBeanDefitionRegistry
 */

public class SjlScannerRegistry implements ImportBeanDefinitionRegistrar, ApplicationContextAware, ResourceLoaderAware {

private ApplicationContext applicationContext;
    private ResourceLoader resourceLoader;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(SjlScanner.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(annotationMetadata, 0));
        }
    }
    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + SjlScannerRegistry.class.getSimpleName() + "#" + index;
    }
    private void registerBeanDefinitions(AnnotationAttributes attributes,BeanDefinitionRegistry registry,String beanName){
        SjlExploreScanner scanner=new SjlExploreScanner(registry);
        Class<? extends Annotation> annotationClass = attributes.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }
        List<String> basePackages=new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(attributes.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(attributes.getStringArray("basePackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(attributes.getClassArray("basePackagesClasses")).map(ClassUtils::getPackageName)
                .collect(Collectors.toList()));
        scanner.setBasePackage(StringUtils.collectionToCommaDelimitedString(basePackages));
        scanner.addIncludeFilter(((metadataReader, metadataReaderFactory) -> true));
        scanner.scan(StringUtils.toStringArray(basePackages));



    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }
}

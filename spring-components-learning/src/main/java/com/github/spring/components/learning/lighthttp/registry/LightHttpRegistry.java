package com.github.spring.components.learning.lighthttp.registry;

import com.github.spring.components.learning.lighthttp.annotation.LightHttpScan;
import com.github.spring.components.learning.lighthttp.scanner.LightHttpClassPathBeanDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jianlei.shi
 * @date 2021/2/25 10:52 上午
 * @description LightHttpRegistry
 */

public class LightHttpRegistry implements ImportBeanDefinitionRegistrar {

    //    private static final String BASE_PACKAGE = "basesPackage";
    private static final String BASE_PACKAGES = "basesPackages";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        final AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(LightHttpScan.class.getName()));
        registryBeanDefinition(annotationAttributes, registry);
    }

    private void registryBeanDefinition(AnnotationAttributes annotationAttributes, BeanDefinitionRegistry registry) {
//        final String packageName = annotationAttributes.getString(BASE_PACKAGE);
        final String[] basesPackageNames = annotationAttributes.getStringArray(BASE_PACKAGES);
        if (basesPackageNames.length > 0){
            LightHttpClassPathBeanDefinitionScanner scanner = new LightHttpClassPathBeanDefinitionScanner(registry);
            scanner.registryAllInterfaces();
            scanner.doScan(basesPackageNames);

        }
    }
}

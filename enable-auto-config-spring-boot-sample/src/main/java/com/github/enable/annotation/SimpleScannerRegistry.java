package com.github.enable.annotation;

import com.github.enable.scan.SjlExploreScanner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
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
 * @description: SjlPostBeanDefinitionRegistry
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#findCandidateComponents(String)  //扫描.class文件 注册BeanDefinition
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#scanCandidateComponents(String)
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitions
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromRegistrars 
 */
@Slf4j
public class SimpleScannerRegistry implements ImportBeanDefinitionRegistrar, ApplicationContextAware, ResourceLoaderAware {

    private ApplicationContext applicationContext;
    private DefaultResourceLoader resourceLoader;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(SimpleScanner.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(annotationMetadata, 0));
        }
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + SimpleScannerRegistry.class.getSimpleName() + "#" + index;
    }

    private void registerBeanDefinitions(AnnotationAttributes attributes, BeanDefinitionRegistry registry, String beanName) {
        SjlExploreScanner scanner = new SjlExploreScanner(registry);
        Class<? extends Annotation> annotationClass = attributes.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }
        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(attributes.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(attributes.getStringArray("basePackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(attributes.getClassArray("basePackagesClasses")).map(ClassUtils::getPackageName)
                .collect(Collectors.toList()));
        scanner.setBasePackage(StringUtils.collectionToCommaDelimitedString(basePackages));
//        scanner.addIncludeFilter(((metadataReader, metadataReaderFactory) -> true));
        scanner.addIncludeFilter(new AnnotationTypeFilter(SimpleService.class));
        scanner.scan(StringUtils.toStringArray(basePackages));


    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description
     * @date 2:00 下午 2020/12/22
     * @see netty-rpc
     * ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
     * resourcePatternResolver.getResources("");
     */

    @SneakyThrows
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (DefaultResourceLoader) resourceLoader;

        Resource resource = this.resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "my-application.yml");
        log.info("application is exists: {}", resource.getFile().exists());

        YamlPropertySourceLoader loader=new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = loader.load("my-application", resource);
        PropertySource<?> propertySource = propertySources.stream().findFirst().orElse(null);
        assert propertySource != null;
        String text = (String) propertySource.getProperty("test.name");
        /*String text = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));*/
        log.info("==>text output :" + text);


    }
}

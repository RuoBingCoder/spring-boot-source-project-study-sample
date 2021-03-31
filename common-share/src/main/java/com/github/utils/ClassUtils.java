package com.github.utils;

import com.github.exception.CommonException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/23 5:09 下午
 * @description ClassUtils
 */

public class ClassUtils {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    protected static final Log logger = LogFactory.getLog(ClassUtils.class);

    private static String resourcePattern = DEFAULT_RESOURCE_PATTERN;
    private static MetadataReaderFactory metadataReaderFactory;
    private static ResourcePatternResolver resourcePatternResolver;

    /**
     * 生成类文件
     *
     * @param clazz     clazz
     * @param proxyName 代理名称
     * @param clazzPath clazz路径
     * @return
     * @author jianlei.shi
     * @date 2021-02-23 17:09:06
     */
    public static void generateProxyClassFile(Class<?> clazz, String proxyName, String clazzPath) {

        //根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
//        String paths = clazz.getResource(".").getPath();
//        System.out.println(paths);
        FileOutputStream out = null;

        try {
            //保留到硬盘中
            out = new FileOutputStream(clazzPath + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 扫描候选组件
     *
     * @param basePackage 基本包
     * @param environment 环境
     * @return {@link Set<BeanDefinition> }
     * @author jianlei.shi
     * @date 2021-02-26 14:42:41
     * packageSeparator
     */
    public static Set<BeanDefinition> scanCandidateComponents(String basePackage, ConfigurableEnvironment environment) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage, environment) + '/' + resourcePattern;
            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {

                if (resource.isReadable()) {
                    try {
                        MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        sbd.setResource(resource);
                        sbd.setSource(resource);
                    } catch (IOException e) {
                        logger.error("scanCandidateComponents error", e);
                        throw new CommonException("scanCandidateComponents error :【" + e.getMessage() + " 】");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException("scanCandidateComponents exception:"+e.getMessage());
        }
        return candidates;

    }

    private static ResourcePatternResolver getResourcePatternResolver() {
        if (ClassUtils.resourcePatternResolver == null) {
            ClassUtils.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return ClassUtils.resourcePatternResolver;
    }

    protected static String resolveBasePackage(String basePackage, ConfigurableEnvironment environment) {
        return org.springframework.util.ClassUtils.convertClassNameToResourcePath(environment.resolveRequiredPlaceholders(basePackage));
    }

    public static final MetadataReaderFactory getMetadataReaderFactory() {
        if (ClassUtils.metadataReaderFactory == null) {
            ClassUtils.metadataReaderFactory = new CachingMetadataReaderFactory();
        }
        return ClassUtils.metadataReaderFactory;
    }

}

package com.github.enable.filter;

import com.github.enable.annotation.SimpleService;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SimpleTypeFilter 自定义注解类型过滤器
 */
public class SimpleTypeFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return metadataReader.getAnnotationMetadata().getClassName().equals(SimpleService.class.getName());
    }
}

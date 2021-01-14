package com.sjl.enable.filter;

import com.sjl.enable.annotation.SimpleService;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SjlTypeFilter
 */
public class SjlTypeFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return metadataReader.getAnnotationMetadata().getClassName().equals(SimpleService.class.getName());
    }
}

package com.github.simple.core.annotation;

import java.util.List;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 2:46 下午
 * @description: SimpleBeanMethod
 */

public class SimpleMethodBean extends ConfigSimpleMethod{


    public SimpleMethodBean(List<SimpleMethodMetadata> methodMetadata, Class<?> configClazz) {
        super(methodMetadata, configClazz);
    }
}

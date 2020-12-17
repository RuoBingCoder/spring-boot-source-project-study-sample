package com.github.simple.core.annotation;

import java.util.List;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 2:46 下午
 * @description: SimpleBeanMethod
 */

public class SimpleBeanMethod extends ConfigSimpleMethod{


    public SimpleBeanMethod(List<SimpleMethodMetadata> methodMetadata, Class<?> configClazz) {
        super(methodMetadata, configClazz);
    }
}

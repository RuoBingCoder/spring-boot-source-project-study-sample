package com.github.simple.core.beans.factory.config;

import cn.hutool.core.util.ObjectUtil;
import com.github.simple.core.beans.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.core.utils.SimpleStringValueResolver;

/**
 * 简单的值解析器
 *
 * @author
 * @author: jianlei.shi
 * @date: 2020/12/23 4:09 下午
 * @description: SimpleEmbeddedValueResolver
 * @date
 */

public class SimpleEmbeddedValueResolver implements SimpleStringValueResolver {
    private final SimpleDefaultListableBeanFactory beanFactory;

    public SimpleEmbeddedValueResolver(SimpleDefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public String resolveStringValue(String strVal) {
        Object resolveStringValue = beanFactory.resolveStringValue(null, strVal);
        if (ObjectUtil.isNull(resolveStringValue)){
            return "";
        }
        return (String) resolveStringValue;
    }
}

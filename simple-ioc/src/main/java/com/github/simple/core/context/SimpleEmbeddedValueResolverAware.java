package com.github.simple.core.context;

import com.github.simple.core.utils.SimpleStringValueResolver;

/**
 * @author: JianLei
 * @date: 2020/12/23 7:14 下午
 * @description: 解析 {@code @SimpleValue}
 */
public interface SimpleEmbeddedValueResolverAware {

    void setEmbeddedValueResolver(SimpleStringValueResolver resolver);

}

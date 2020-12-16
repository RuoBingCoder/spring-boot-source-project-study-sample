package com.github.simple.core.factory;

import org.springframework.context.annotation.Lazy;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 5:03 下午
 * @description: 延迟加载
 *
 */
@Lazy
public interface SimpleObjectProvider<T> extends SimpleObjectFactory<T> {
    //TODO
}

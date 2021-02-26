package com.github.spring.components.learning.lighthttp.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jianlei.shi
 * @date 2021/2/26 1:42 下午
 * @description LightHttpHolder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightHttpHolder<T> {

    private T t;

    private Object[] args;

    private ThreadPoolExecutor executor;

    private Class<?> returnType;


}

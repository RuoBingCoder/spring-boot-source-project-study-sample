package com.github.simple.core.env;

import com.github.simple.core.resource.SimplePropertySource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jianlei.shi
 * @date 2021/2/14 9:10 下午
 * @description 配置信息source工厂
 */

public class SimpleMutablePropertySources implements SimplePropertySources {
    public static final String SIMPLE_MUTABLE_PROPERTY_SOURCES_BEAN_NAME = "simpleMutablePropertySources";
    private final List<SimplePropertySource<?>> propertySources = new CopyOnWriteArrayList<>();


    @Override
    public <T> void addFirst(SimplePropertySource<T> simplePropertySource) {
        removeSource(simplePropertySource);
        propertySources.add(0, simplePropertySource);
    }

    private <T> void removeSource(SimplePropertySource<T> simplePropertySource) {
        propertySources.remove(simplePropertySource);
    }

    @Override
    public <T> void addLast(SimplePropertySource<T> simplePropertySource) {
        removeSource(simplePropertySource);
        propertySources.add(simplePropertySource);
    }

    @Override
    public List<SimplePropertySource<?>> getSimplePropertySources() {
        return propertySources;
    }
}

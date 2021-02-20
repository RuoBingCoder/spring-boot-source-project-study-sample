package com.github.nacos.sample.config.factory;

import com.github.nacos.sample.config.Config;
import com.github.nacos.sample.config.source.ConfigPropertySource;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author jianlei.shi
 * @date 2021/2/19 7:04 下午
 * @description ConfigPropertySourceFactory
 */

public class ConfigPropertySourceFactory {

    private final List<ConfigPropertySource> configPropertySources = Lists.newLinkedList();

    public ConfigPropertySource getConfigPropertySource(String name, Config source) {
        ConfigPropertySource configPropertySource = new ConfigPropertySource(name, source);

        configPropertySources.add(configPropertySource);

        return configPropertySource;
    }

    public List<ConfigPropertySource> getAllConfigPropertySources() {
        return Lists.newLinkedList(configPropertySources);

    }
}

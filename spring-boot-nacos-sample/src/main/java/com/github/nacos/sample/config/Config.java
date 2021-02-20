package com.github.nacos.sample.config;

import com.github.nacos.sample.config.listener.ConfigChangeListener;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:46 下午
 * @description: Config
 */
public interface Config {

    String getProperty(String key, String defaultValue);

    void addChangeListener(ConfigChangeListener listener);


}

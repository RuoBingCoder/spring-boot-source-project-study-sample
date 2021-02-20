package com.github.nacos.sample.config.source;

import com.github.nacos.sample.config.Config;
import com.github.nacos.sample.config.listener.ConfigChangeListener;
import org.springframework.core.env.EnumerablePropertySource;

/**
 * @author jianlei.shi
 * @date 2021/2/19 7:05 下午
 * @description ConfigPropertySource
 */

public class ConfigPropertySource extends EnumerablePropertySource<Config> {

    public ConfigPropertySource(String name, Config source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return new String[0];
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name,null);
    }


    public void addChangeListener(ConfigChangeListener listener) {
        this.source.addChangeListener(listener);
    }
}

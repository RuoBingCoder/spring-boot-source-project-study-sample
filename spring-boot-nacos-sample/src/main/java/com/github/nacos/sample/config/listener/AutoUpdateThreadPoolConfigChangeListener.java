package com.github.nacos.sample.config.listener;

import com.github.nacos.sample.config.event.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author jianlei.shi
 * @date 2021/2/22 3:51 下午
 * @description AutoUpdateThreadPoolConfigChangeListener
 */
@Slf4j
public class AutoUpdateThreadPoolConfigChangeListener implements ConfigChangeListener{
    private final ConfigurableBeanFactory beanFactory;
    private final ConfigurableEnvironment environment;

    public AutoUpdateThreadPoolConfigChangeListener(ConfigurableBeanFactory beanFactory, ConfigurableEnvironment environment) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {

    }
}

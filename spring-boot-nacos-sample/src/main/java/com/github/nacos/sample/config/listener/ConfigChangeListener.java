package com.github.nacos.sample.config.listener;


import com.github.nacos.sample.config.event.ConfigChangeEvent;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:49 下午
 * @description ConfigChangeListener
 */

public interface ConfigChangeListener {

    void onChange(ConfigChangeEvent changeEvent);

}

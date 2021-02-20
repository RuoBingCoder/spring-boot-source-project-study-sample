package com.github.nacos.sample.config.listener;

import com.github.nacos.sample.config.SpringValue;
import com.github.nacos.sample.config.event.ConfigChangeEvent;
import com.github.nacos.sample.support.ValuePostProcessor;
import helper.PlaceholderHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import utils.SpringUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:55 下午
 * @description AutoUpdateConfigChangeListener
 */
@Slf4j
public class AutoUpdateConfigChangeListener implements ConfigChangeListener {
    private final ConfigurableBeanFactory beanFactory;
    private final ConfigurableEnvironment environment;

    public AutoUpdateConfigChangeListener(ConfigurableEnvironment environment, ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        final Set<String> changedKeys = changeEvent.changedKeys();
        final Map<String, SpringValue> valueTargetMap = ValuePostProcessor.valueTargetMap;
        final Iterator<Map.Entry<String, SpringValue>> iterator = valueTargetMap.entrySet().iterator();
        while (iterator.hasNext()) {
            for (String key : changedKeys) {
                final Map.Entry<String, SpringValue> next = iterator.next();
                final Object value = SpringUtils.getBeanByType(PlaceholderHelper.class).resolvePropertyValue(beanFactory, next.getKey(), key);
                final SpringValue springValue = next.getValue();
                final Object bean = springValue.getBeanRef().get();
                update(value, bean, springValue.getField());
            }

        }
    }

    private void update(Object value, Object bean, Field field) {
        try {
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            log.error("field set value error", e);
        }


    }
}

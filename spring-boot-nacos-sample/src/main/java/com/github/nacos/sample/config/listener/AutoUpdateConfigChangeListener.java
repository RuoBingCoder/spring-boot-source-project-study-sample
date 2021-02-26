package com.github.nacos.sample.config.listener;

import com.github.nacos.sample.config.SpringValue;
import com.github.nacos.sample.config.event.ConfigChangeEvent;
import com.github.nacos.sample.support.ThreadPoolValuePostProcessor;
import helper.PlaceholderHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import utils.SpringUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:55 下午
 * @description AutoUpdateConfigChangeListener
 */
@Slf4j
public abstract class AutoUpdateConfigChangeListener implements ConfigChangeListener {
    private  ConfigurableBeanFactory beanFactory;
    private  ConfigurableEnvironment environment;
    protected final Map<String, SpringValue> targetValueMap = new ConcurrentHashMap<>();

    protected AutoUpdateConfigChangeListener(ConfigurableEnvironment environment, ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    protected AutoUpdateConfigChangeListener() {
    }

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        final Set<String> changedKeys = changeEvent.changedKeys();
        final Iterator<Map.Entry<String, SpringValue>> iterator = targetValueMap.entrySet().iterator();
        List<String> filterChangedKeys;
        while (iterator.hasNext()) {
            final Map.Entry<String, SpringValue> next = iterator.next(); //key beanName
            //key ${..}
            if (this instanceof ThreadPoolValuePostProcessor) {
                filterChangedKeys = changedKeys.stream().filter(k -> !"${app.name}".equals(k)).collect(Collectors.toList());
            } else {
                filterChangedKeys = changedKeys.stream().filter("${app.name}"::equals).collect(Collectors.toList());

            }
            for (String key : filterChangedKeys) {
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

    protected SpringValue getSpringValue(Object o, String beanName) {
        return new SpringValue(beanName, getFields(o).get(0), new WeakReference<>(o));
    }

    protected List<Field> getFields(Object bean) {
        List<Field> fieldList = new ArrayList<>();
        final Class<?> beanClass = bean.getClass();
        final Field[] fields = beanClass.getDeclaredFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    fieldList.add(field);
                }
            }
        }
        return fieldList;
    }

    @Data
    static class ValueTarget {
        private Object bean;
        private Class<?> type;

        private List<Field> fields;
    }

    protected abstract Map<String, SpringValue> getValues();
}

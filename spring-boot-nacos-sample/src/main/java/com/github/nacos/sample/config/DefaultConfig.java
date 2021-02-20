package com.github.nacos.sample.config;

import com.github.nacos.sample.config.event.ConfigChangeEvent;
import com.github.nacos.sample.config.listener.ConfigChangeListener;
import com.google.common.collect.Lists;
import enums.ThreadTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import utils.ThreadPoolUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jianlei.shi
 * @date 2021/2/19 7:14 下午
 * @description DefaultConfig
 */
@Component
@Slf4j
public class DefaultConfig implements Config, InitializingBean {

    /**
     * 配置属性动态改变 spring
     */
    private final AtomicReference<Properties> m_configProperties;
    private final List<ConfigChangeListener> m_listeners = Lists.newCopyOnWriteArrayList();

    private final Map<String, ConfigChange> m_changes = new ConcurrentHashMap<>();
    private final String[] names = {"qq", "weChat", "抖音", "网易云音乐", "王者"};
    private final ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) ThreadPoolUtils.getExecutor(ThreadTypeEnum.SCHEDULED, true);


    public DefaultConfig() {
        this.m_configProperties = new AtomicReference<>();
        initConfigProperties();
    }

    private void initConfigProperties() {
        Properties properties = new Properties();
        properties.put("app.name", "apollo");
        this.m_configProperties.set(properties);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return m_configProperties.get().getProperty(key);
    }

    @Override
    public void addChangeListener(ConfigChangeListener listener) {
        if (!m_listeners.contains(listener)) {
            m_listeners.add(listener);
        }
    }


    public void sync() {
        if (!CollectionUtils.isEmpty(m_listeners)) {
            for (ConfigChangeListener m_listener : m_listeners) {
                executor.scheduleAtFixedRate(() -> {
                    log.info("-->>onChange thread start!");
                    m_listener.onChange(new ConfigChangeEvent("simple-config", m_changes));
                }, 2, 5, TimeUnit.SECONDS);
            }
        }
    }


    @Override
    public void afterPropertiesSet() {
        AtomicInteger count = new AtomicInteger();
        executor.scheduleAtFixedRate(() -> {
            log.info("-->>init thread start!");

            if (m_changes.get("${app.name}") != null) {
                m_changes.remove("${app.name}");
            }

            m_changes.put("${app.name}", new ConfigChange(null, names[count.getAndIncrement()]));
            m_configProperties.get().clear();
            m_configProperties.get().setProperty("app.name", names[count.get()]);
            if (count.get() >= names.length-1) {
                count.set(0);
            }


        }, 2, 5, TimeUnit.SECONDS);
    }
}

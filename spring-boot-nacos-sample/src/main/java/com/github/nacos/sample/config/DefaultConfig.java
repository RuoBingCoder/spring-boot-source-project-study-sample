package com.github.nacos.sample.config;

import com.github.common.constants.Constants;
import com.github.exception.CommonException;
import com.github.helper.ThreadPoolHelper;
import com.github.http.RequestParam;
import com.github.nacos.sample.config.event.ConfigChangeEvent;
import com.github.nacos.sample.config.listener.ConfigChangeListener;
import com.github.utils.HttpUtils;
import com.github.utils.PropertiesUtils;
import com.github.utils.SpringUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

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
    private ScheduledThreadPoolExecutor executor;

    public DefaultConfig() {
        this.m_configProperties = new AtomicReference<>();
        initConfigProperties();
    }

    private void initConfigProperties() {
        Properties properties = new Properties();
        properties.put("app.name", "apollo");
        properties.put("simple.thread.pool.corePoolSize", 4);
        properties.put("simple.thread.pool.maxPoolSize", 8);
        properties.put("simple.thread.pool.keepAliveTime", 2);
        properties.put("simple.thread.pool.initialDelay", 2);
        properties.put("simple.thread.pool.period", 2);
        properties.put("simple.thread.pool.delay", 2);
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


    /**
     * 同步 此处通知
     *
     * @return
     * @author jianlei.shi
     * @date 2021-02-23 14:15:03
     */
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
        ThreadPoolHelper threadPoolHelper = SpringUtils.getBeanByType(ThreadPoolHelper.class);
        if (threadPoolHelper == null) {
            throw new CommonException("threadPoolHelper is null!");
        }
        executor = (ScheduledThreadPoolExecutor) threadPoolHelper.getExecutor(enums.ThreadTypeEnum.SCHEDULED, true);
        startMonitor(executor);
        AtomicInteger count = new AtomicInteger();
        //发送http请求定时轮询
        executor.scheduleAtFixedRate(() -> {
            log.info("-->>init thread start!");

            HttpUtils.doPost(getRequestParam());
            if (m_changes.get("${app.name}") != null) {
                m_changes.remove("${app.name}");
            }

//            m_changes.put("${app.name}", new ConfigChange(null, names[count.getAndIncrement()]));
            setChanges(m_changes, count);
            m_configProperties.get().clear();
            configPropertiesSetValue(m_configProperties, count);
//            m_configProperties.get().setProperty("app.name", names[count.get()]);
            if (count.get() >= names.length - 1) {
                count.set(0);
            }


        }, 2, 5, TimeUnit.SECONDS);
    }

    private RequestParam getRequestParam() {
        final Properties properties = PropertiesUtils.load();
        RequestParam rq = new RequestParam();
        if (properties.get(Constants.ALPHA_URL) != null || "".equals(properties.get(Constants.ALPHA_URL))) {
            rq.setIsPost(true);
            rq.setSource((String) properties.get(Constants.ALPHA_URL));
            rq.setBody(getBody());
        }
        return rq;
    }

    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("id", 101);
        body.put("name", "app");
        body.put("address", "beijing");
        return body;
    }

    private void startMonitor(ScheduledThreadPoolExecutor executor) {
        new Thread(new WorkerWatch(executor), "monitor-thread-pool").start();

    }

    private void setChanges(Map<String, ConfigChange> m_changes, AtomicInteger count) {
        m_changes.put("${app.name}", new ConfigChange(null, names[count.getAndIncrement()]));
        m_changes.put("${simple.thread.pool.corePoolSize}", new ConfigChange(null, "2" + names[count.getAndIncrement()]));
        m_changes.put("${simple.thread.pool.maxPoolSize}", new ConfigChange(null, 4 + names[count.getAndIncrement()]));
        m_changes.put("${simple.thread.pool.keepAliveTime}", new ConfigChange(null, "4"));
        m_changes.put("${simple.thread.pool.initialDelay}", new ConfigChange(null, "4"));
        m_changes.put("${simple.thread.pool.period}", new ConfigChange(null, "20"));
        m_changes.put("${simple.thread.pool.delay}", new ConfigChange(null, "20"));

    }

    private void configPropertiesSetValue(AtomicReference<Properties> m_configProperties, AtomicInteger count) {
        m_configProperties.get().setProperty("app.name", names[count.get()]);
        m_configProperties.get().setProperty("simple.thread.pool.corePoolSize", "2" + names[count.get()]);
        m_configProperties.get().setProperty("simple.thread.pool.maxPoolSize", "4" + names[count.get()]);
        m_configProperties.get().setProperty("simple.thread.pool.keepAliveTime", "4");
        m_configProperties.get().setProperty("simple.thread.pool.initialDelay", "4");
        m_configProperties.get().setProperty("simple.thread.pool.period", "20");
        m_configProperties.get().setProperty("simple.thread.pool.delay", "20");

    }

    static class WorkerWatch implements Runnable {
        private volatile Boolean isStop = false;
        private final ScheduledThreadPoolExecutor executor;

        public WorkerWatch(ScheduledThreadPoolExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void run() {
            while (!isStop && !Thread.currentThread().isInterrupted()) {
                try {
                    sleep(5000);
                    log.info("====>>>current thread info core :{} max:{} queue size:{} task count: {} active count:{}", executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getQueue().size(), executor.getTaskCount(), executor.getActiveCount());
                } catch (Exception e) {
                    isStop = true;
                    log.error("cache-thread-pool monitor error", e);
                    throw new RuntimeException("cache-thread-pool monitor error");
                }
            }
        }
    }
}

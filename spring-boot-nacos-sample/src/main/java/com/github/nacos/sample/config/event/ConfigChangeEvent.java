package com.github.nacos.sample.config.event;

import com.github.nacos.sample.config.ConfigChange;
import lombok.*;

import java.util.Map;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:51 下午
 * @description ConfigChangeEvent
 */
@Setter
@Getter
@AllArgsConstructor
public class ConfigChangeEvent {

    private final String m_namespace;
    private final Map<String, ConfigChange> m_changes;

    public Set<String> changedKeys() {
        return m_changes.keySet();
    }
}

package com.github.spring.components.learning.condition;

import com.github.spring.components.learning.config.BeanConfig;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author jianlei.shi
 * @date 2021/1/24 2:33 下午
 * @description SimpleCondition
 * @see BeanConfig
 */

public class SimpleCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return "mac".equals(context.getEnvironment().getProperty("os.name"));
    }
}

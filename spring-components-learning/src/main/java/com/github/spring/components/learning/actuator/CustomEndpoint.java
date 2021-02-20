package com.github.spring.components.learning.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/2/19 12:28 下午
 * @description 自定义监控端点
 * @since 2.0
 * <a href="https://www.baeldung.com/spring-boot-actuators"/>
 */
@Endpoint(id = "custom") //http://localhost:8080/actuator/custom
@Slf4j
public class CustomEndpoint implements EnvironmentAware {

    private Environment environment;

    @ReadOperation //Get  请求
    public Map<String, Object> invoke() {
        Map<String, Object> actuatorMap = new HashMap<>();
        actuatorMap.put(environment.getProperty("spring.redis.host"), "test");
        log.info("-->>>>spring.redis.host :{}", environment.getProperty("spring.redis.host") == null ? "null" : environment.getProperty("spring.redis.host"));
        return actuatorMap;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

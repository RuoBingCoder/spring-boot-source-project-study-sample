package com.github.custom.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/17 3:48 下午
 * @description:
 */
@ConfigurationProperties(prefix = "thread.pool")
@Component
@Setter
@Getter
public class ThreadPoolPropertiesConfig {

    private Integer coreSize=4;

    private Integer maxSize=8;

    private Long keepAliveTime;

}

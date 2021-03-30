package com.github.simple.demo.service.config;

import com.github.simple.core.annotation.SimpleBean;
import com.github.simple.core.annotation.SimpleConfig;
import com.github.simple.core.annotation.SimpleValue;
import com.github.simple.demo.service.RedisTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/18 11:08 上午
 * @description: RedisConfig
 * {@link SimpleConfig}
 * {@link SimpleBean}
 */
@SimpleConfig
@Slf4j
public class RedisConfig {


    @SimpleValue("${redis.name}")
    private String redisName;

    @SimpleValue("${redis.maxConnect}")
    private Integer redisMaxConnect;
    @SimpleValue("${redis.host}")
    private String host;

    @SimpleBean
    public RedisTemplate redisTemplate() {
        log.info("获取redisName is:{}", redisName == null ? " null " : redisName);
        return new RedisTemplate(redisName,redisMaxConnect,host);
    }
}

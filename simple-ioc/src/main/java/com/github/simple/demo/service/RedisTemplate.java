package com.github.simple.demo.service;

/**
 * @author: jianlei.shi
 * @date: 2020/12/18 11:10 上午
 * @description: RedisTemplate
 */

public class RedisTemplate {

    private String redisName;

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public RedisTemplate(String redisName) {
        this.redisName = redisName;
    }

    @Override
    public String toString() {
        return "RedisTemplate{" +
                "redisName='" + redisName + '\'' +
                '}';
    }
}

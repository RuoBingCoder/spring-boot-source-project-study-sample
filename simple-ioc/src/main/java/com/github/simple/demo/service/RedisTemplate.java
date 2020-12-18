package com.github.simple.demo.service;

/**
 * @author: jianlei.shi
 * @date: 2020/12/18 11:10 上午
 * @description: RedisTemplate
 */

public class RedisTemplate {

    private String redisName;

    private String max;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public RedisTemplate(String redisName, String max) {
        this.redisName = redisName;
        this.max = max;
    }

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
                ", max='" + max + '\'' +
                '}';
    }
}

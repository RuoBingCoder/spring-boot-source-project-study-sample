package com.github.simple.demo.service;

/**
 * @author: jianlei.shi
 * @date: 2020/12/18 11:10 上午
 * @description: RedisTemplate
 */

public class RedisTemplate {

    private String redisName;

    private Integer max;

    private String host;

    public RedisTemplate(String redisName, Integer max, String host) {
        this.redisName = redisName;
        this.max = max;
        this.host = host;
    }

    public RedisTemplate(String redisName, Integer max) {
        this.redisName = redisName;
        this.max = max;
    }

    public RedisTemplate() {
    }

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "RedisTemplate{" +
                "redisName='" + redisName + '\'' +
                ", max=" + max +
                ", host='" + host + '\'' +
                '}';
    }
}

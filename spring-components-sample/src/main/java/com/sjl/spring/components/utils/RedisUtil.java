package com.sjl.spring.components.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/6 下午2:20
 * @description: RedisUtil
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @param company key
     * @param point   经纬度
     * @param address 地点
     */
    public void geoAdd(String company, Point point, String address) {
        stringRedisTemplate.opsForGeo().add(company, point, address);


    }

    /**
     * 获取经纬度
     *
     * @param k
     * @param members
     * @return
     */
    public List<Point> geoPos(String k, String... members) {
        return stringRedisTemplate.opsForGeo().position(k, members);
    }

    /**
     * 计算距离
     *
     * @param key
     * @param addr1
     * @param addr2
     * @return
     */
    public Distance geoDist(String key, String addr1, String addr2) {
        return stringRedisTemplate.opsForGeo().distance(key, addr1, addr2, RedisGeoCommands.DistanceUnit.KILOMETERS);

    }

    /**
     * redis 位操作
     *
     * @param k
     * @param f
     * @param v
     * @return
     */
    public Boolean setBit(String k, Long f, Boolean v) {
        return stringRedisTemplate.opsForValue().setBit(k, f, v);

    }

    /**
     * @param k
     * @param offset
     * @return
     */
    public Boolean getBit(String k, Long offset) {
        return stringRedisTemplate.opsForValue().getBit(k, offset);
    }

    public void setHash(String k, Object hash, Object value) {
        stringRedisTemplate.opsForHash().put(k, hash, value);
    }


    public Long pfCount(String k) {
        return stringRedisTemplate.opsForHyperLogLog().size(k);
    }

    public Long pfAdd(String k, String... value) {
        return stringRedisTemplate.opsForHyperLogLog().add(k, value);
    }

    /**
     * lua脚本
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean setKey(String expire, String key, String val) {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(Boolean.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/lock.lua")));
        List<String> keys = Arrays.asList(key, val);
        return stringRedisTemplate.execute(script, keys, expire);
    }

    /**
     * zset
     *
     * @param key
     * @param val
     * @return
     */
    public Long zAdd(String key, String... val) {
        return stringRedisTemplate.opsForSet().add(key, val);
    }


    /**
     * leftPush
     *
     * @param key
     * @param value
     * @return
     * @description 操作list
     */
    public Long leftPushAll(String key, String... value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * hash 操作
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, Object hashKey, Object value) {
        stringRedisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

}

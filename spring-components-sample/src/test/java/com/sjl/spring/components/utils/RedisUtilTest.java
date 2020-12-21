package com.sjl.spring.components.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RedisUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * å¿«æ·é®: command+shift+t
 * @since <pre>12月 6, 2020</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest// æå®å¯å¨ç±»
public class RedisUtilTest {

    @Resource
    private RedisUtil redisUtil;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: geoAdd(Object company, Point point, Object address)
     */
    @Test
    public void testGeoAdd() throws Exception {
//        116.323849,40.053723  116.323849,40.053723
        Map<String , Point> pointHashMap=new HashMap<>();
        pointHashMap.put("meituan",new Point(116.494761,40.014702));
        pointHashMap.put("baidu",new Point(116.307899,40.057038));
        pointHashMap.put("xiaomi",new Point(116.323849,40.053723));
        pointHashMap.put("wangyi",new Point(116.282632,40.04933));

        pointHashMap.forEach((key, value) -> redisUtil.geoAdd("company", value, key));
    }

    /**
     * Method: getGeo(Object k, Object keys)
     */
    @Test
    public void testGetGeo() throws Exception {
        List<Point> objectList = redisUtil.geoPos("company", "xinlang");
        System.out.println("==>"+ JSONObject.toJSONString(objectList));
    }

    @Test
    public void testGeoDist() throws Exception {
        Distance distance = redisUtil.geoDist("company", "xiaomi", "jd");
        System.out.println("==>"+ JSONObject.toJSONString(distance.getValue()));
    }
    @Test
    public void setTest(){
        Boolean res = redisUtil.setKey("100", "bejing", "故宫");
        System.out.println("==>"+res);

    }

    @Test
    public void hashTest(){
    redisUtil.hSet("Internet","小米","米家");
    }
    @Test
    public void leftPushTest(){
    redisUtil.leftPushAll("subject","语文","数学","物理");
    }

    @Test
    public void addTest(){
//        redisUtil.setKey("100000","watch:key","obejct");
        redisUtil.modifyValueForKey("watch:key","object_modify_2");

    }
   
} 

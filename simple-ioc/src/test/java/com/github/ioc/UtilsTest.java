package com.github.ioc;

import com.github.simple.core.utils.YamlUtils;

import static com.github.simple.core.utils.YamlUtils.getProperty;

/**
 * @author jianlei.shi
 * @date 2021/1/14 10:46 上午
 * @description UtilsTest
 */

public class UtilsTest {

    public static void main(String[] args) {
            YamlUtils.loadYaml("simple.yaml");
//        String host = getProperty("spring.redis.host");
//        System.out.println("==>host : " + host);
//        String pwd = getProperty("spring.redis.password");
//        System.out.println("==>pwd : " + pwd);
//        String name = getProperty("name");
//        System.out.println("==>name : " + name);
            String addr = getProperty("com.github.address");
            System.out.println("==>>addr: "+addr);
        }
}

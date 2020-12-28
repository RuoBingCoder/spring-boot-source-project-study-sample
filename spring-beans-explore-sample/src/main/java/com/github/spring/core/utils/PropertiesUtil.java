package com.github.spring.core.utils;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: JianLei
 * @date: 2020/10/31 4:24 下午
 * @description: 多环境配置文件读取
 */
@Slf4j
public class PropertiesUtil {

    private static final List<Properties> ALL_PROPERTIES_LIST = new CopyOnWriteArrayList<>();
    private static final String RESOURCE_PATH = "my-application.properties";
    private static final String MY_SPRING_PROFILE = "my-spring.profile";
    private static final String DEV_PROFILE = "dev";
    private static final String TEST_PROFILE = "test";

    static {
        try {
            log.info("--->>>>>PropertiesUtil static init<<<<----");
            Properties defaultProperties = new Properties();
            defaultProperties.load(getFileInputStream(RESOURCE_PATH));
            ALL_PROPERTIES_LIST.add(defaultProperties);
            handleProfile(defaultProperties);
        } catch (IOException e) {
            log.error("load my-application.properties exception!", e);
        }

    }

    private static void handleProfile(Properties properties) throws IOException {
        String profile = properties.getProperty(MY_SPRING_PROFILE);
        if (profile == null) {
            return;
        }
        if (DEV_PROFILE.equals(profile.trim())) {
            Properties devProperties = new Properties();
            devProperties.load(getFileInputStream(propertiesNamesHandle("-dev")));
            ALL_PROPERTIES_LIST.add(devProperties);
        } else if (TEST_PROFILE.equals(profile.trim())) {
            Properties testProperties = new Properties();
            testProperties.load(getFileInputStream(propertiesNamesHandle("-test")));
            ALL_PROPERTIES_LIST.add(testProperties);
        }


    }

    private static String propertiesNamesHandle(String suffix) {
        StringBuilder sb = new StringBuilder();
        String[] split = RESOURCE_PATH.split("\\.");
        sb.append(split[0]).append(suffix).append(".").append(split[1]);
        return sb.toString();

    }

    public static String getProperties(String key) throws IOException {
        Properties result;
        if (ALL_PROPERTIES_LIST.size() == 2) {
            result = ALL_PROPERTIES_LIST.get(1);

        } else {
            result = ALL_PROPERTIES_LIST.get(0);
        }
        return result.getProperty(key);


    }


    public static InputStream getFileInputStream(String sourceName) {
        Assert.notNull(sourceName, "sourceName is not null!");
        InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(sourceName);
        Assert.notNull(resourceAsStream, "获取 source stream is error!");
        return resourceAsStream;
    }


}

package com.github.simple.core.utils;

import com.github.simple.core.exception.SimplePropertiesLoadException;
import com.github.simple.core.resource.SimpleResource;

import java.io.*;
import java.util.Properties;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:20 下午
 * @description: PropertyUtils
 */

public class PropertyUtils {
    private static final Properties props = new Properties();

    public static Properties load(SimpleResource resource) {
        try {
            props.load(resource.getInputStream());
            return props;
        } catch (Exception e) {
            throw new SimplePropertiesLoadException("Properties load exception" + e.getMessage());
        }

    }

    public static void store(OutputStream os, String commentMsg) throws IOException {
        props.store(os, commentMsg);

    }

    public static void main(String[] args) throws IOException {
       //将Properties文件内容写入磁盘 dubbo
        File file = new File("/Users/shijianlei/IdeaProjects/learning-spring-sample/simple-ioc/src/main/resources/store.properties");
        FileOutputStream fos = new FileOutputStream(file);
        props.setProperty("name.server","127.0.0.1");
        PropertyUtils.store(fos, "test store");
        props.forEach((key, value) -> System.out.println(key + "->" + value));
    }
}

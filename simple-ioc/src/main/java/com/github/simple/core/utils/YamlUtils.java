package com.github.simple.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.simple.core.resource.SimplePropertySource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jianlei.shi
 * @date 2021/1/13 8:20 下午
 * @description yaml工具类
 * @see org.springframework.beans.factory.config.YamlPropertiesFactoryBean
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YamlUtils extends Yaml {

    private static final String DEFAULT_YAML_FILE_NAME = "application.yaml";
    private static Map<String, Object> yamlSource;
    private static final Map<String, Object> YAML_PROPERTY_CACHE = new ConcurrentHashMap<>();
    private String yamlFileName;


    public YamlUtils() {
    }

    public static YamlUtils getInstance() {
        return new YamlUtils();

    }

    /**
     * 负载yaml
     *
     * @param yamlFileName yaml文件名称
     */
    public static void loadYaml(String yamlFileName) {
        YamlUtils instance = getInstance();
        InputStream inputStream;
        if (yamlFileName == null) {
            inputStream = getInputStream(null);
        } else {
            inputStream = getInputStream(yamlFileName);
        }
        yamlSource = instance.load(inputStream);
    }

    private static InputStream getInputStream(String yamlFileName) {
        return ClassUtils.getDefaultClassLoader()
                .getResourceAsStream(yamlFileName == null ? DEFAULT_YAML_FILE_NAME : yamlFileName);

    }

    /**
     * 获取属性值
     *
     * @param key 关键字
     * @return {@link String}
     */
    public static String getProperty(String key) {
        if (CollectionUtil.isNotEmpty(yamlSource)) {
            Object result = YAML_PROPERTY_CACHE.get(key);
            if (ObjectUtil.isNotNull(result)) {
                return buildResType(YAML_PROPERTY_CACHE.get(key));
            }
            Map<String, Object> temp = yamlSource;
            return handleOriginData(key,temp);

        }
        YAML_PROPERTY_CACHE.put(key, yamlSource.get(key));
        return buildResType(yamlSource.get(key));
    }

    /**
     * 框架配置源文件yaml读取
     *
     * @param key            关键
     * @param propertySource 来源
     * @return {@link String}
     */
    public static String getPropertyBySource(String key, SimplePropertySource<Map<String, Object>> propertySource) {
        if (CollectionUtil.isNotEmpty(propertySource.getValue())) {
            Object result = YAML_PROPERTY_CACHE.get(key);
            if (ObjectUtil.isNotNull(result)) {
                return buildResType(YAML_PROPERTY_CACHE.get(key));
            }
            Map<String, Object> temp = propertySource.getValue();
            return handleOriginData(key,temp);

        }
        YAML_PROPERTY_CACHE.put(key, propertySource.getValue().get(key));
        return buildResType(propertySource.getValue().get(key));
    }

    /**
     * 处理源数据
     *
     * @param key  关键
     * @param temp 临时
     * @return {@link String}
     */
    private static String handleOriginData(String key, Map<String, Object> temp) {
        String[] split = key.split("\\.");
        int i = 0;
        String res = "";
        while (split.length > 1 && i < split.length) {
            Object o1 = temp.get(split[i++]);
            if (o1 instanceof Map) {
                temp = (Map<String, Object>) o1;
            } else {
                YAML_PROPERTY_CACHE.put(key, o1);
                res = buildResType(o1);

            }
        }
        return res;

    }

    /**
     * 构建返回值类型
     *
     * @param o1 o1
     * @return {@link String}
     */
    private static String buildResType(Object o1) {
        if (o1 instanceof Integer) {
            return String.valueOf(o1);
        }
        if (o1 instanceof Long) {
            return String.valueOf(o1);
        }
        if (o1 instanceof Double) {
            return String.valueOf(o1);

        }
        return (String) o1;
    }

    public static Map<String, Object> loadForIs(InputStream inputStream) {
        YamlUtils instance = getInstance();
        return instance.load(inputStream);
    }
}

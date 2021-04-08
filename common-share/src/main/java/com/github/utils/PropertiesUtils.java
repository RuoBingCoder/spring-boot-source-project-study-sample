package com.github.utils;

import com.github.common.constants.Constants;
import com.github.exception.CommonException;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author jianlei.shi
 * @date 2021/2/23 2:23 下午
 * @description PropertiesUtils
 */

public class PropertiesUtils {
    private static final Properties properties = new Properties();

    public static Properties load() {
        try {
            load(Constants.APP_PROPERTIES_NAME);
        } catch (Exception e) {
            throw new CommonException("load() app properties error: 【" + e.getMessage() + " 】");

        }
        return properties;
    }

    /**
     * 获取配置信息
     *
     * @param environment 环境
     * @param prefix      前缀
     * @param targetClass 目标类
     * @return {@link Object }
     * @author jianlei.shi
     * @date 2021-02-24 16:51:51
     */
    public static Object getBinderProperties(Environment environment, String prefix, Class<?> targetClass) {
        try {
            Class<?> binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
            Method getMethod = binderClass.getDeclaredMethod("get", Environment.class);
            Method bindMethod = binderClass.getDeclaredMethod("bind", String.class, Class.class);
            //静态方法不需要传Obj
            Object binderObject = getMethod.invoke(null, environment);
            String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;
            Object bindResultObject = bindMethod.invoke(binderObject, prefixParam, targetClass);
            Method resultGetMethod = bindResultObject.getClass().getDeclaredMethod("get");
            return resultGetMethod.invoke(bindResultObject);
        } catch (Exception ex) {
            throw new RuntimeException("getBinderProperties error");
        }
    }

    private static void load(String name) {
        try {
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(name));
        } catch (Exception e) {
            throw new CommonException("load( params ) app properties error: 【" + e.getMessage() + " 】");
        }
    }
}

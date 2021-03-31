package com.github.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/3/31 3:55 下午
 * @description ReflectUtils
 */

public class ReflectUtils {

    public static void setAccessible(Object object) {
        try {
            if (object instanceof Field) {
                Field field = (Field) object;
                field.setAccessible(true);
            } else if (object instanceof Method) {
                Method method = (Method) object;
                method.setAccessible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Method[] getMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }


    public static boolean isStrOrHashOrEq(Method method) {
        return "toString".equals(method.getName()) || "equals".equals(method.getName()) || "hashCode".equals(method.getName());
    }


}

package com.github.utils;

import java.lang.reflect.*;

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
                if ((!Modifier.isPublic(field.getModifiers()) ||
                        !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                        Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
                    field.setAccessible(true);
                }
            } else if (object instanceof Method) {
                Method method = (Method) object;
                if ((!Modifier.isPublic(method.getModifiers()) ||
                        !Modifier.isPublic(method.getDeclaringClass().getModifiers()) ||
                        Modifier.isFinal(method.getModifiers())) && !method.isAccessible()) {
                    method.setAccessible(true);
                }
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

    /**
     * 得到通用的单一类型
     *
     * @param source 源
     * @return {@link T}
     */
    public static <T> T getGenericSingleType(Class<?> source) {
        final Type[] genericInterfaces = source.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            final Type genericInterface = genericInterfaces[0];
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                return (T) actualTypeArguments[0];
            }
        }
        return null;


    }
}

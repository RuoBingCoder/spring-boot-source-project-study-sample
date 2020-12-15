package com.github.simple.ioc.utils;

import com.github.simple.ioc.annotation.SimpleAutowired;
import com.github.simple.ioc.annotation.SimpleComponent;
import com.github.simple.ioc.annotation.SimpleService;
import com.github.simple.ioc.enums.SimpleIOCEnum;
import com.github.simple.ioc.exception.SimpleFieldTypeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

/**
 * @author: JianLei
 * @date: 2020/12/12 2:10 下午
 * @description: IOCReflectionUtils
 */

public class ReflectUtils {

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static LinkedHashMap<String, Field> findAutowired(Class<?> clazz) {
        return matchType(clazz);

    }

    private static LinkedHashMap<String, Field> matchType(Class<?> clazz) {
        return matchField(clazz.getDeclaredFields());
    }

    private static LinkedHashMap<String, Field> matchField(Field[] declaredFields) {
        LinkedHashMap<String, Field> beanFields = new LinkedHashMap<>(20);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(SimpleAutowired.class)) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new SimpleFieldTypeException(SimpleIOCEnum.STATIC_FIELD_NOT_INJECT.getMsg());
                }
                beanFields.put(field.getType().getSimpleName(), field);
            }
        }
        return beanFields;
    }


    public static Boolean matchAnnotationComponent(Class<?> clazz) {
        return clazz.isAnnotationPresent(SimpleComponent.class) || clazz.isAnnotationPresent(SimpleService.class);
    }


}

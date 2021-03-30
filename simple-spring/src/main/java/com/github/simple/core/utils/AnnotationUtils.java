package com.github.simple.core.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 11:47 上午
 * @description: AnnotationUtils
 */

public class AnnotationUtils {


    public static boolean isCandidateClass(Class<?> targetClass, Class<? extends Annotation> annotationType) {
        LinkedHashMap<String, Field> autowiredAnnotation = ReflectUtils.findAutowiredAnnotation(targetClass, annotationType);
        return !CollectionUtil.isEmpty(autowiredAnnotation);
    }
}

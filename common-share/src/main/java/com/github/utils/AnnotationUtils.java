package com.github.utils;

import com.github.exception.CommonException;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author jianlei.shi
 * @date 2021/2/22 10:48 上午
 * @description AnnotationUtils
 */

public class AnnotationUtils {

    public static Set<Class<?>> getImportMetadata(Class<?> clazz) {
        Set<Class<?>> classes = new HashSet<>();
        processImport(classes, clazz);
        return classes;
    }

    public static void processImport(Set<Class<?>> classes, Class<?> clazz) {
        final Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getAnnotation(Import.class) != null) {
                final Class<?>[] classes1 = annotation.annotationType().getAnnotation(Import.class).value();
                classes.addAll(Arrays.asList(classes1));
            }
        }
    }

    public static <T extends Annotation> T getAnnotationMetaData(Class<?> clazz, Class<T> annotation) {
        if (!clazz.isAnnotationPresent(annotation)) {
            throw new CommonException("The interfaces annotation not exist @" + annotation.getSimpleName());
        }
        return clazz.getAnnotation(annotation);
    }

    /**
     * 获取每个方法所有注解集合
     *
     * @param clazz clazz
     * @return {@link Map<Method, Set<T>> }
     * @author jianlei.shi
     * @date 2021-03-31 16:31:06
     */
    public static <T extends Annotation> Map<Method, Set<T>> getAllMethodAnnotation(Class<?> clazz) {
        final Method[] methods = ReflectUtils.getMethods(clazz);
        return mergeAllAttributes(methods);
    }

    private static <T extends Annotation> Map<Method, Set<T>> mergeAllAttributes(Method[] methods) {
        Map<Method, Set<T>> mergeResult = new LinkedHashMap<>();
        if (methods.length > 0) {
            for (Method method : methods) {
                if (!ReflectUtils.isStrOrHashOrEq(method)) {
                    ReflectUtils.setAccessible(method);
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    List<T> list = (List<T>) Arrays.asList(annotations);
                    Set<T> annotationsSet = new HashSet<>(list);
                    mergeResult.putIfAbsent(method, annotationsSet);
                }

            }
        }
        return mergeResult;

    }

    /**
     * 匹配指定注释是否在方法上
     *
     * @param clazz      clazz
     * @param annotation 注释
     * @return {@link Map<Method,T> }
     * @author jianlei.shi
     * @date 2021-03-31 16:30:44
     */
    public static <T extends Annotation> Map<Method, T> getAssignAnnotationOnMethods(Class<?> clazz, Class<T>[] annotation) {
        final Method[] methods = ReflectUtils.getMethods(clazz);
        return mergeAttributes(methods, annotation);
    }

    public static <T extends Annotation> Map<Method, T> mergeAttributes(Method[] methods, Class<T>[] annotation) {
        Map<Method, T> annotations = new HashMap<>();
        for (Method method : methods) {
            for (Class<T> at : annotation) {
                if (method.isAnnotationPresent(at)) {
                    annotations.putIfAbsent(method, method.getAnnotation(at));
                }
            }
        }
        return annotations;

    }


    public static AnnotationAttributes getAnnotationAttributes(Annotation annotation) {
        return org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation, false, false);
    }


    public static <T extends Annotation> Map<Field, T> getAssignAnnotationOnFields(Class<?> clazz, Class<T> annotationType) {
        final Field[] fields = ReflectUtils.getFields(clazz);
        Map<Field, T> fieldAnnotationMerge = new LinkedHashMap<>();
        if (fields.length > 0) {
            for (Field field : fields) {
                ReflectUtils.setAccessible(field);
                if (field.isAnnotationPresent(annotationType)) {
                    fieldAnnotationMerge.putIfAbsent(field, field.getAnnotation(annotationType));
                }
            }
        }
        return fieldAnnotationMerge;
    }


}

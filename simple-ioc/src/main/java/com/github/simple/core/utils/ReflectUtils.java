package com.github.simple.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.github.simple.core.annotation.SimpleAspect;
import com.github.simple.core.annotation.SimpleComponentScan;
import com.github.simple.core.annotation.SimpleValue;
import com.github.simple.core.beans.SimpleFactoryBean;
import com.github.simple.core.enums.SimpleIOCEnum;
import com.github.simple.core.exception.SimpleFieldTypeException;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.demo.test.PrepareSimpleApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.util.ReflectUtil.getMethods;

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

    public static Boolean checkFieldModifier(Field clazz) {
        return commonTypeCheck(clazz.getModifiers());
    }

    private static Boolean commonTypeCheck(int modifiers) {
        return Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)
                || Modifier.isPrivate(modifiers) || Modifier.isAbstract(modifiers)
                || Modifier.isInterface(modifiers) || Modifier.isProtected(modifiers);
    }

    public static Boolean checkClassModifier(Class<?> clazz) {
        return commonTypeCheck(clazz.getModifiers());
    }

    public static Boolean checkMethodModifier(Method clazz) {
        return commonTypeCheck(clazz.getModifiers());
    }

    public static LinkedHashMap<String, Field> findAutowiredAnnotation(Class<?> clazz, Class<? extends Annotation> autowiredType) {
        return matchType(clazz, autowiredType);

    }

    private static LinkedHashMap<String, Field> matchType(Class<?> clazz, Class<? extends Annotation> autowiredType) {
        return matchField(clazz.getDeclaredFields(), autowiredType);
    }

    private static LinkedHashMap<String, Field> matchField(Field[] declaredFields, Class<? extends Annotation> autowiredType) {
        LinkedHashMap<String, Field> beanFields = new LinkedHashMap<>(20);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(autowiredType)) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new SimpleFieldTypeException(SimpleIOCEnum.STATIC_FIELD_NOT_INJECT.getMsg());
                }
                beanFields.put(field.getName(), field);
            }
        }
        return beanFields;
    }


    public static <T extends Annotation> Boolean matchAnnotationComponent(Class<?> clazz, Class<T> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static Boolean matchAspect(Class<?> clazz) {
        return clazz.isAnnotationPresent(SimpleAspect.class);

    }

    public static <T extends Annotation> Method getMethodWithAnnotation(Class<?> clazz, Class<T> annotation) {
        Map<Method, T> methodAndAnnotation = getMethodAndAnnotation(clazz, annotation);
        return methodAndAnnotation.keySet().stream().findFirst().get();
    }

    public static <T extends Annotation> Map<Method, T> getMethodAndAnnotation(Class<?> clazz, Class<T> annotation) {
        return MethodIntrospector.selectMethods(clazz,
                (MethodIntrospector.MetadataLookup<T>) method -> AnnotatedElementUtils
                        .findMergedAnnotation(method, annotation));
    }

    public static String getBasePackages(Class<?> clazz) {
        if (clazz.isAnnotationPresent(SimpleComponentScan.class)) {
            return clazz.getAnnotation(SimpleComponentScan.class).basePackages();
        }
        throw new SimpleIOCBaseException("包路径不能为空!");
    }

    public static boolean resolveValueDependency(Member member) {
        Field field = (Field) member;
        if (!field.isAnnotationPresent(SimpleValue.class)) {
            return false;
        }
        if (field.getType().equals(String.class)) {
            return true;
        }
        if (field.getType().equals(Integer.class)) {
            return true;
        }
        return field.getType().equals(Long.class);
    }

    public static String parseValue(Field field) {
        SimpleValue simpleValue = field.getAnnotation(SimpleValue.class);
        Assert.notNull(simpleValue.value(), "Annotation SimpleValue value is null!");
        return simpleValue.value();
    }

    public static String resolveTypeBeanNames(String beanName, List<SimpleFactoryBean> simpleFactoryBeans) {
        if (CollectionUtil.isEmpty(simpleFactoryBeans)) {
            return beanName;
        }
        for (SimpleFactoryBean simpleFactoryBean : simpleFactoryBeans) {
            Type[] genericInterfaces = simpleFactoryBean.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface.getTypeName().contains(beanName)) {
                    return ClassUtils.toLowerBeanName(simpleFactoryBean.getClass().getSimpleName());
                }
            }
        }
        return null;
    }

    public static <T> T getGenericSingleType(Class<?> source){
        final Type[] genericInterfaces = source.getGenericInterfaces();
        if (genericInterfaces.length>0){
            final Type genericInterface = genericInterfaces[0];
            if (genericInterface instanceof ParameterizedType){
                ParameterizedType parameterizedType= (ParameterizedType) genericInterface;
                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                return (T) actualTypeArguments[0];
            }
        }
        return null;
    }

    public static Boolean isAssignableFrom(Class<?> clazz, Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    public static boolean matchMethodParameterType(Class<?> clazz, Class<?> tyClass){
        final Method[] methods = getMethods(clazz);
        int count = 0;
        for (Method method : methods) {
            if ("onApplicationEvent".equals(method.getName())) {
                count++;
                
            }
        }
        return count == 1;
    }
    public static Class<?> getMethodParameter(Method method) {
        method.setAccessible(false);
        return getMethodParameters(method)[0];
    }
    public static Class<?>[] getMethodParameters(Method method) {
        method.setAccessible(false);
        return method.getParameterTypes();
    }

    public static void main(String[] args) {
        final Object genericSingleType = getGenericSingleType(PrepareSimpleApplicationListener.class);
        System.out.println(genericSingleType);
    }
}

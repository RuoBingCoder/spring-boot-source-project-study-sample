package com.github.spring.dependency.inject.demo.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.github.helper.PlaceholderHelper;
import com.github.spring.dependency.inject.demo.annotation.CustomAsync;
import com.github.spring.dependency.inject.demo.annotation.CustomAutowired;
import com.github.spring.dependency.inject.demo.annotation.CustomValue;
import com.github.spring.dependency.inject.demo.bean.MyAsyncHolder;
import com.github.spring.dependency.inject.demo.proxy.ProxyFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/9/25 12:12 下午
 * @description:
 */
@Component
public class ReflectiveUtil implements ApplicationContextAware, EnvironmentAware {


    public static final String PLACEHOLDER_PREFIX = "${";
    public static final String PLACEHOLDER_SUFFIX = "}";
    private static ApplicationContext applicationContext;
    private static Environment environment;

    public static void inject(Object bean, Class<? extends Annotation> clazz)
            throws Exception {
        Field[] fields = getField(bean.getClass(), null);

        for (Field field : fields) {
            if (field.isAnnotationPresent(clazz)) {
                parseField(bean, applicationContext, field, environment, clazz);
            }
        }
    }

    private static void parseField(Object bean, ApplicationContext context, Field field, Environment env, Class<? extends Annotation> clazz) throws Exception {
        field.setAccessible(true);

        Annotation annotation = field.getAnnotation(clazz);
        if (annotation instanceof CustomValue) {
            CustomValue myValue = (CustomValue) annotation;
            String value = resolvePlaceHolderValue(myValue);
           /* String property = env.getProperty(value);
            if (StringUtils.isEmpty(property)) {
                throw new RuntimeException("property value [" + value + "] is null");
            }*/
            setFieldValue(bean, value, field);
        } else if (annotation instanceof CustomAutowired) {
            MyAsyncHolder myAsyncHolder = new MyAsyncHolder();
            Object obj = context.getBean(field.getType());
            if (obj == null) {
                throw new RuntimeException("bean is not found");
            }
            myAsyncHolder.setBean(obj.getClass());
            //获取异步处理方法添加到list中
            Map<Method, CustomAsync> methodAndAnnotation = getMethodAndAnnotation(obj, CustomAsync.class);
            List<Method> methodList = new ArrayList<>(methodAndAnnotation.keySet());
            if (CollectionUtil.isNotEmpty(methodList)) {
                myAsyncHolder.setMethods(methodList);
                field.set(bean, new ProxyFactory(myAsyncHolder).getObject());
            } else {
                field.set(bean, obj);

            }

        }
    }

    public static <T extends Annotation> Map<Method, T> getMethodAndAnnotation(Object bean, Class<T> annotation) {
        return MethodIntrospector.selectMethods(bean.getClass(),
                (MethodIntrospector.MetadataLookup<T>) method -> AnnotatedElementUtils
                        .findMergedAnnotation(method, annotation));
    }


    private static void setFieldValue(Object bean, String property, Field field)
            throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(bean, property);
        } else if (field.getType().equals(Integer.class)) {
            field.set(bean, Integer.valueOf(property));
        }
    }

    private static String resolvePlaceHolderValue(CustomValue myValue) {
        String value = myValue.value();
        PlaceholderHelper helper = applicationContext.getBean(PlaceholderHelper.class);
        final String var = (String) helper.resolvePropertyValue((ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory(), null, value);
        if (StringUtils.isNotBlank(var)){
            return var;
        }
       /* int startInterceptionIndex = value.indexOf(PLACEHOLDER_PREFIX);
        int endInterceptionIndex = value.indexOf(PLACEHOLDER_SUFFIX);
        String v = value.substring(startInterceptionIndex + 2, endInterceptionIndex);
        if (StringUtils.isNotBlank(v)) {
            return v;
        }*/
        throw new RuntimeException(PLACEHOLDER_PREFIX + value + PLACEHOLDER_SUFFIX + "is null!");
    }

    public static void main(String[] args) {
        String value = "${app.name}";
        int startInterceptionIndex = 0;
        int endInterceptionIndex = value.indexOf(PLACEHOLDER_SUFFIX);
        String v = value.substring(startInterceptionIndex + 2, endInterceptionIndex);
        System.out.println(v);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ReflectiveUtil.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        ReflectiveUtil.environment = environment;
    }


    public static Field[] getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (fieldName == null) {
            return clazz.getDeclaredFields();
        }
        Field field = clazz.getDeclaredField(fieldName);
        return new Field[]{field};
    }

    public static Method[] getMethods(Class<?> clazz, String methodName) throws NoSuchMethodException {
        if (methodName == null) {
            return clazz.getDeclaredMethods();
        }
        Method method = clazz.getDeclaredMethod(methodName);
        return new Method[]{method};
    }

}

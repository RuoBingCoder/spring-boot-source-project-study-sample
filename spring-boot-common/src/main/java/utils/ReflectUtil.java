package utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/9/26 1:07 下午
 * @description:
 */
@Slf4j
public class ReflectUtil {

    public static Map<Object, List<Object>> parseBeanAndMethodsAnnotationInfo(
            Object obj, Class<? extends Annotation> annotationTypes) {
        Map<Object, List<Object>> methods = new LinkedHashMap<>();
        boolean isTrue = obj.getClass().isAnnotationPresent(annotationTypes);
        List<Object> classesAnnotations = new ArrayList<>();
        classesAnnotations.add(annotationTypes);
        methods.put(obj, classesAnnotations);
        if (isTrue) {
            for (Method method : obj.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                Annotation[] annotations = method.getAnnotations();
                if (annotations.length > 0) {
                    List<Object> annotationsList = new ArrayList<>(Arrays.asList(method.getAnnotations()));
                    methods.put(method, annotationsList);
                }
            }
        }
        return methods;
    }


    public static void threadClassLoader(String name) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class<?> clazz = Class.forName(name, false, Thread.currentThread().getContextClassLoader());
        log.info("===>currentThread class is:{}", clazz.getName());
        Constructor<?> constructor = clazz.getConstructor(String.class);
        log.info("----->>>:{}", constructor.newInstance("hello word!").toString());
    }

    /**
     * @return boolean
     * @Author jianlei.shi
     * @Description 匹配class 或者 method 是否有条件注解
     * @Date 下午1:54 2020/11/28
     * @Param
     **/
    public static Boolean findConditions(Object bean, Class<? extends Annotation> annotation) {
        if (checkParameters(bean, annotation)) {
            throw new IllegalArgumentException("The params is null!");
        }
        if (findClassConditions(bean, annotation)) {
            return true;
        }
        if (!findClassConditions(bean, annotation)) {
            return findMethodConditions(bean, annotation);
        }
        return false;

    }

    public static boolean findMethodConditions(Object bean, Class<? extends Annotation> annotation) {
        Class<?> clazz = bean.getClass();
        return clazz.isAnnotationPresent(annotation);
    }

    public static boolean findClassConditions(Object bean, Class<? extends Annotation> annotation) {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            return Arrays.stream(methods).allMatch(m -> m.isAnnotationPresent(annotation));
        }
        return false;
    }

    public static boolean checkParameters(Object bean, Class<? extends Annotation> annotation) {
        return bean == null || annotation == null;
    }

}

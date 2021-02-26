package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleValue;
import utils.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: jianlei.shi
 * @date: 2020/12/28 11:10 上午
 * @description: AnnotationTest
 */
public class AnnotationTest {

    @SimpleValue("简单的")
    private String man;



    /**
     * AnnotationInvocationHandler 处理注解
     * @param args
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
//        Annotation
       Field[] fields = AnnotationTest.class.getDeclaredFields();
        Field field = fields[0];
        SimpleValue annotation = field.getAnnotation(SimpleValue.class); //进行反射jvm会将runtime注解信息封装map->传递给代理类,在进行调用自定义注解方法时会从map中获取
        String values = (String) annotation.getClass().getMethod("value").invoke(annotation);
        System.out.println("==>"+values);
        ClassUtils.generateProxyClassFile(SimpleValue.class,"ProxySimpleValue","");


    }
}

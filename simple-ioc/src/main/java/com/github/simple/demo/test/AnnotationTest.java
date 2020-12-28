package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleValue;

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



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = AnnotationTest.class.getDeclaredFields();
        Field field = fields[0];
        SimpleValue annotation = field.getAnnotation(SimpleValue.class);
        String values = (String) annotation.getClass().getMethod("value").invoke(annotation);
        System.out.println("==>"+values);

    }
}

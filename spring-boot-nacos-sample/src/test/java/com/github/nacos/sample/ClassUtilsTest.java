package com.github.nacos.sample;


import com.github.helper.ThreadPoolHelper;

/**
 * @author jianlei.shi
 * @date 2021/2/13 5:05 下午
 * @description ClassUtilsTest
 */

public class ClassUtilsTest {

    public static void main(String[] args) throws ClassNotFoundException {
        final ClassLoader classLoader = ClassUtilsTest.class.getClassLoader();
        final ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        final ClassLoader classLoader2 = Thread.currentThread().getClass().getClassLoader();

        final Class<?> aClass = classLoader1.loadClass(ThreadPoolHelper.class.getName());
        System.out.println(aClass.getName());
//        ClassUtils.resolveGenericType(ClassUtilsTest.class);
    }
}

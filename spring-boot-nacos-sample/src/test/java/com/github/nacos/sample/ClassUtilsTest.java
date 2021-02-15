package com.github.nacos.sample;

import com.alibaba.spring.util.ClassUtils;

/**
 * @author jianlei.shi
 * @date 2021/2/13 5:05 下午
 * @description ClassUtilsTest
 */

public class ClassUtilsTest {

    public static void main(String[] args) {
        ClassUtils.resolveGenericType(ClassUtilsTest.class);
    }
}

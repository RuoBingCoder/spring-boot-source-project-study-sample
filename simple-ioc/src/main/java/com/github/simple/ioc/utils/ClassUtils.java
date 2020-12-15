package com.github.simple.ioc.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.github.simple.ioc.annotation.SimpleOrdered;
import com.github.simple.ioc.definition.SimpleRootBeanDefinition;
import com.github.simple.ioc.enums.SimpleIOCEnum;
import com.github.simple.ioc.exception.SimpleIOCBaseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:14 下午
 * @description: class util
 */
public class ClassUtils {

    public static Set<Class<?>> scannerBasePackages(String basePackages) {
        if (checkParams(basePackages)) {
            throw new SimpleIOCBaseException(SimpleIOCEnum.BASE_PACKAGES_NOT_NULL.getMsg());
        }
        return cn.hutool.core.util.ClassUtil.scanPackage(basePackages);
    }

    private static Boolean checkParams(String basePackages) {
        return StringUtils.isBlank(basePackages);
    }


    public static Boolean isNull(Object obj) {
        return !ObjectUtil.isNull(obj);
    }


    public static String toLowerBeanName(String beanName) {
        if (ObjectUtil.isNull(beanName)) {
            throw new SimpleIOCBaseException(SimpleIOCEnum.PARAMETER_NOT_NULL.getMsg());
        }
        char[] chars = beanName.trim().toCharArray();
        chars[0] = (char) (chars[0] + 32);
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(toLowerBeanName("HelloWord"));
    }

    public static Object newInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static Class<?> getClass(Object obj){
        Assert.notNull(obj,"obj notNull");
        if (obj instanceof SimpleRootBeanDefinition){
            SimpleRootBeanDefinition srb= (SimpleRootBeanDefinition) obj;
            return srb.getRootClass();
        }
        return obj.getClass();
    }

    public static boolean matchOrdered(Object obj){
        return obj.getClass().isAnnotationPresent(SimpleOrdered.class);

    }

    public static Integer getOrderedValue(Object obj){
        SimpleOrdered ordered = obj.getClass().getAnnotation(SimpleOrdered.class);
        return ordered.value();
    }
}

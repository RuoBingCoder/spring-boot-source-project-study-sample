package com.sjl.boot.autowired.inject.sample.utils;

import com.sjl.boot.autowired.inject.sample.annotation.MyAutowired;
import com.sjl.boot.autowired.inject.sample.annotation.MyValue;
import com.sjl.boot.autowired.inject.sample.proxy.RpcServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author: JianLei
 * @date: 2020/9/25 12:12 下午
 * @description:
 */
public class ReflectiveUtil {

  public static final String VALUE_PREFIX = "${";
  public static final String VALUE_SUFFIX = "}";

  public static void inject(Object bean, Environment env, Class<? extends Annotation> clazz)
      throws IllegalAccessException {
    Field[] fields = bean.getClass().getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(clazz)) {
        parseField(bean, field, env, clazz);
      }
    }
  }

  private static void parseField(
      Object bean, Field field, Environment env, Class<? extends Annotation> clazz)
      throws IllegalAccessException {
    field.setAccessible(true);
    Annotation annotation = field.getAnnotation(clazz);
    if (annotation instanceof MyValue) {
      MyValue myValue = (MyValue) annotation;
      String value = resolvePlaceHolder(myValue);
      String property = env.getProperty(value);
      if (StringUtils.isEmpty(property)) {
        throw new RuntimeException("property value [" + value + "] is null");
      }
      setFieldValue(bean, property, field);
    } else if (annotation instanceof MyAutowired) {
      MyAutowired myAutowired = (MyAutowired) annotation;
      if (field.getType().isInterface()) {
        System.out.println(
            "version is:" + myAutowired.version() + "====>" + "group is:" + myAutowired.group());
        field.set(bean, new RpcServiceFactory<>(field.getType()).getObject());
      }
    }
  }

  private static void setFieldValue(Object bean, String property, Field field)
      throws IllegalAccessException {
    if (field.getType().equals(String.class)) {
      field.set(bean, property);
    } else if (field.getType().equals(Integer.class)) {
      field.set(bean, Integer.valueOf(property));
    }
  }

  private static String resolvePlaceHolder(MyValue myValue) {
    String value = myValue.value();
    int startInterceptionIndex = value.indexOf(VALUE_PREFIX);
    int endInterceptionIndex = value.indexOf(VALUE_SUFFIX);
    String v = value.substring(startInterceptionIndex + 2, endInterceptionIndex);
    if (StringUtils.isNotBlank(v)) {
      return v;
    }
    throw new RuntimeException(VALUE_PREFIX + value + VALUE_SUFFIX + "is null!");
  }

  public static void main(String[] args) {
    String value = "${app.name}";
    int startInterceptionIndex = value.indexOf(VALUE_PREFIX);
    int endInterceptionIndex = value.indexOf(VALUE_SUFFIX);
    String v = value.substring(startInterceptionIndex + 2, endInterceptionIndex);
    System.out.println(v);
  }
}

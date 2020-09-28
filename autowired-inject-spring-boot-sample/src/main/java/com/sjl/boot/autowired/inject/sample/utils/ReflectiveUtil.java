package com.sjl.boot.autowired.inject.sample.utils;

import com.sjl.boot.autowired.inject.sample.annotation.MyAsync;
import com.sjl.boot.autowired.inject.sample.annotation.MyAutowired;
import com.sjl.boot.autowired.inject.sample.annotation.MyValue;
import com.sjl.boot.autowired.inject.sample.bean.MyAsyncHolder;
import com.sjl.boot.autowired.inject.sample.proxy.MyAsyncFactoryBean;
import com.sjl.boot.autowired.inject.sample.proxy.ProxyFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/9/25 12:12 下午
 * @description:
 */
@Component
public class ReflectiveUtil {

  public static final String VALUE_PREFIX = "${";
  public static final String VALUE_SUFFIX = "}";

  public static void inject(Object bean,ApplicationContext context, Environment env, Class<? extends Annotation> clazz)
          throws Exception {
    Field[] fields = bean.getClass().getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(clazz)) {
        parseField(bean,context, field, env, clazz);
      }
    }
  }

  private static void parseField(
      Object bean,ApplicationContext context, Field field, Environment env, Class<? extends Annotation> clazz)
          throws Exception {
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
      if (field.getType().isInterface()) {
        MyAsyncHolder myAsyncHolder=new MyAsyncHolder();
        Map<String, ?> beansOfType = context.getBeansOfType(field.getType());
        for (Map.Entry<String, ?> entry : beansOfType.entrySet()) {
          myAsyncHolder.setBean(entry.getValue().getClass());
          //获取异步处理方法添加到list中
          List<Method> asyncMethods = getAsyncMethods(entry.getValue().getClass());
          myAsyncHolder.setMethods(asyncMethods);
        }

        field.set(bean, new MyAsyncFactoryBean(myAsyncHolder).getObject());
      }
    }
  }

  private static List<Method> getAsyncMethods(Class<?> aClass) {
    List<Method> methods=new LinkedList<>();
    for (Method method : aClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent(MyAsync.class)){
        methods.add(method);
      }
    }
    return methods;
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
    int startInterceptionIndex = 0;
    int endInterceptionIndex = value.indexOf(VALUE_SUFFIX);
    String v = value.substring(startInterceptionIndex + 2, endInterceptionIndex);
    System.out.println(v);
  }


}

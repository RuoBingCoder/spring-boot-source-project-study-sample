package com.sjl.tomcat.core.utils;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.ClassUtil;
import com.sjl.tomcat.core.annotation.EasyController;
import com.sjl.tomcat.core.annotation.EasyService;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:38 下午
 * @description: ClassScannerUtil
 */
@Setter
@Getter
public class ClassScannerUtil {

  public static <T> void scan(String packageName, T t)
      throws IllegalAccessException, InstantiationException {
    if (packageName == null || "".equals(packageName)) {
      throw new IllegalArgumentException("扫描包路径不能为null");
    }
    if (t instanceof Map) {
      Map<String, Object> beanMaps = (Map<String, Object>) t;
      Set<Class<?>> classes = ClassScanner.scanPackage(packageName);
      for (Class<?> clazz : classes) {
        if (!clazz.isInterface()) {
          if (clazz.isAnnotationPresent(EasyService.class)
              || clazz.isAnnotationPresent(EasyController.class)) {
            beanMaps.put(caseFirstClassName(clazz.getSimpleName()), clazz.newInstance());
          }
        }
      }
    }
  }



  public static String caseFirstClassName(String className){
    char[] chars = className.toCharArray();
    chars[0]+=32;
    return new String(chars);
  }

  public static void main(String[] args) {
    System.out.println(caseFirstClassName("Bean"));
  }
}

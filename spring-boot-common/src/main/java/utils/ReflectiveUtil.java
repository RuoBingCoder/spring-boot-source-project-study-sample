package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/9/26 1:07 下午
 * @description:
 */
public class ReflectiveUtil {

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
}

package utils;

import exception.CommonException;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author jianlei.shi
 * @date 2021/2/22 10:48 上午
 * @description AnnotationUtils
 */

public class AnnotationUtils {

    public static Set<Class<?>> getImportMetadata(Class<?> clazz) {
        Set<Class<?>> classes = new HashSet<>();
        processImport(classes, clazz);
        return classes;
    }

    public static void processImport(Set<Class<?>> classes, Class<?> clazz) {
        final Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getAnnotation(Import.class) != null) {
                final Class<?>[] classes1 = annotation.annotationType().getAnnotation(Import.class).value();
                classes.addAll(Arrays.asList(classes1));
            }
        }
    }

    public static Annotation getAnnotationMetaData(Class<?> clazz, Class<? extends Annotation> annotation) {
        if (!clazz.isAnnotationPresent(annotation)) {
            throw new CommonException("The interfaces annotation not exist @" + annotation.getSimpleName());
        }
        return clazz.getAnnotation(annotation);
    }


    public static Map<Method, Class<? extends Annotation>> findMethodAnnotations(Class<?> clazz, Class<? extends Annotation>[] annotation) {
        final Method[] methods = ClassUtils.getMethods(clazz);
        Map<Method, Class<? extends Annotation>> methodAnnotationMap = findAnnotationMethod(methods, annotation);
        return methodAnnotationMap;
    }

    public static Map<Method, Class<? extends Annotation>> findAnnotationMethod(Method[] methods, Class<? extends Annotation>[] annotation) {
        Map<Method, Class<? extends Annotation>> annotations = new HashMap<>();
        for (Method method : methods) {
            for (Class<? extends Annotation> at : annotation) {
                if (method.isAnnotationPresent(at)) {
                    annotations.put(method, at);
                }
            }
        }
        return annotations;

    }


}

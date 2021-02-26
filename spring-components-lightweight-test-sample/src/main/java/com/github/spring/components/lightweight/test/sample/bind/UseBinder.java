package com.github.spring.components.lightweight.test.sample.bind;

import lombok.SneakyThrows;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/2/24 3:39 下午
 * @description UseBinder
 */
//@Component
public class UseBinder implements EnvironmentAware {
    private Environment environment;
    private String attr;

   /* public UseBinder(String attr) {
        this.attr = attr;
    }
*/


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    @SneakyThrows
    public Object v2(Environment environment, String prefix, Class<?> targetClass) {
        try {
            Class<?> binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
            Method getMethod = binderClass.getDeclaredMethod("get", Environment.class);
            Method bindMethod = binderClass.getDeclaredMethod("bind", String.class, Class.class);
            //静态方法不需要传Obj
            Object binderObject = getMethod.invoke(null, this.environment);
            String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;
            Object bindResultObject = bindMethod.invoke(binderObject, prefixParam, targetClass);
            Method resultGetMethod = bindResultObject.getClass().getDeclaredMethod("get");
            return resultGetMethod.invoke(bindResultObject);
        } catch (Exception var10) {
            throw new RuntimeException("v2 error");
        }
    }

    public static String test(String str) {
        String res = str + "." + str;
        return res;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = UseBinder.class.getDeclaredMethod("test", String.class);
        Object res = method.invoke(null, "hello");
        System.out.println(res.toString());
    }
}

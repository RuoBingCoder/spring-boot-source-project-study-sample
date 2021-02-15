package com.github.spring.components.learning;

import com.github.spring.components.learning.compile.support.JavassistCompiler;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author jianlei.shi
 * @date 2021/1/26 4:28 下午
 * @description Hello
 */

public class HelloWord {
    
    public String output(String param){
        if (param==null){
            throw new IllegalArgumentException("param is not null");
        }
        return "test:->"+param;
    }

    public static void main(String[] args) throws Exception {
        File file=new File("/Users/shijianlei/IdeaProjects/learning-spring-sample/spring-components-learning/src/main/resources/Hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        final String hello = IOUtils.toString(fileInputStream);
        JavassistCompiler compiler=new JavassistCompiler();
        final Class<?> clazz = compiler.compile(hello, HelloWord.class.getClassLoader());
        final Object o = clazz.newInstance();
        final Method method = clazz.getMethod("output", String.class);
        final Object result = method.invoke(o, "张三");
        System.out.println("result->"+result);

    }
}

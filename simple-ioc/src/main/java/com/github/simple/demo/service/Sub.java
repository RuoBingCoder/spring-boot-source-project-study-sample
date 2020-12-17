package com.github.simple.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleBean;
import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: jianlei.shi
 * @date: 2020/12/14 4:24 下午
 * @description: Sub
 */
@SimpleComponent
public class Sub extends Parent{


    public void test2(){
        System.out.println("test2");
    }

    @SimpleBean
    public Teacher teacher(){
        return new Teacher("zhangsan");
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<Method, SimpleBean> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(Sub.class, SimpleBean.class);
        Method method = methodAndAnnotation.keySet().stream().findFirst().get();
        System.out.println(method.getReturnType());
        Teacher invoke = (Teacher) method.invoke(ClassUtils.newInstance(Sub.class), null);
        System.out.println(JSONObject.toJSONString(method.invoke(ClassUtils.newInstance(Sub.class), null)));
    }
}

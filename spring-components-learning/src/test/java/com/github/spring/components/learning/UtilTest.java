package com.github.spring.components.learning;

import com.alibaba.fastjson.JSONObject;
import com.github.utils.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/3/8 3:44 下午
 * @description UtilTest
 */

public class UtilTest {

    public static void methodAnnotationTest(){
        final Map<Method, Set<Annotation>> methodAnnotation = AnnotationUtils.getAllMethodAnnotation(HelloWord.class);
        methodAnnotation.forEach((key, value) -> {
            System.out.println("key->"+key.getName()+"---value->"+ JSONObject.toJSONString(value));

        });
    }
    public static void main(String[] args) {
//        final LightHttpClient annotation = LightHttpOperateService.class.getAnnotation(LightHttpClient.class);
//        final AnnotationAttributes attributes = AnnotationUtils.getAnnotationAttributes(annotation);
//        final String baseUrl = attributes.getString("baseUrl");
//        System.out.println(baseUrl);
        methodAnnotationTest();
    }
}

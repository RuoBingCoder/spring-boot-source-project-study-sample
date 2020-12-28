package com.github.spring.core.annotation;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/9 4:24 下午
 * @description:
 */
@Component
public class MyAutowiredAnnotationBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor {
    public MyAutowiredAnnotationBeanPostProcessor() {
        super();
        super.setAutowiredAnnotationType(MyAutowired.class);


    }
}

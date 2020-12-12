package com.github.simple.ioc.annotation;

import lombok.Data;

import java.lang.reflect.Member;
import java.util.Collection;

/**
 * @author: JianLei
 * @date: 2020/12/12 2:01 下午
 * @description: InjectMeta
 */
@Data
public class InjectMeta {

    private Class<?> targetClass;

    private Collection<InjectElement> injectElements;

    private Object bean;


    public void inject(Object bean) throws Throwable {
        Collection<InjectElement> injectElements = this.injectElements;
        for (InjectElement injectElement : injectElements) {
            injectElement.inject(bean);
        }


    }
    public static class InjectElement extends SimpleAutowiredAnnotationBeanPostProcessor.InjectFieldElement{
        public InjectElement(Member member, boolean isField, String elementName) {
            super(member, isField, elementName);

        }


    }
}

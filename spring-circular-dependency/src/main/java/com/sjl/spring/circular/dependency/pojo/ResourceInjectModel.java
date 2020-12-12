package com.sjl.spring.circular.dependency.pojo;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/12/2 下午7:28
 * @description: ResourceInjectModel
 * {@code @Resource 解析}
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyMergedBeanDefinitionPostProcessors
 * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(RootBeanDefinition, Class, String)
 */
@Component
public class ResourceInjectModel {

   /* @Resource
    private B b;*/
   public static void main(String[] args) {

   }

}

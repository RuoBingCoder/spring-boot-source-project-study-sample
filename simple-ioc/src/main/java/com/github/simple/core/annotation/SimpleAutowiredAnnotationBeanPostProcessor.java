package com.github.simple.core.annotation;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.factory.SimpleAutowiredCapableBeanFactory;
import com.github.simple.core.factory.SimpleBeanFactory;
import com.github.simple.core.factory.SimpleBeanFactoryAware;
import com.github.simple.core.utils.ReflectUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author: JianLei
 * @date: 2020/12/12 1:56 下午
 * @description: SimpleAutowiredAnnotationBeanPostProcessor
 */
@Slf4j
public class SimpleAutowiredAnnotationBeanPostProcessor  implements SimpleInstantiationAwareBeanPostProcessor, SimpleBeanFactoryAware {

    public static SimpleAutowiredCapableBeanFactory beanFactory;

    @Override
    public Boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public void postProcessProperties(Object bean, String beanName) throws Throwable {
        InjectMeta injectMeta = findAutowired(bean);
        injectMeta.inject(bean);

    }

    private InjectMeta findAutowired(Object bean) {
        LinkedHashMap<String, Field> fieldLinkedHashMap = ReflectUtils.findAutowired(bean.getClass());
        return getInjectMeta(fieldLinkedHashMap,bean);
    }

    private InjectMeta getInjectMeta(LinkedHashMap<String, Field> fields, Object bean) {
        InjectMeta injectMeta = new InjectMeta();
        Collection<InjectMeta.InjectElement> elements=new ArrayList<>();
        if (CollectionUtil.isNotEmpty(fields)) {
            fields.forEach((key, value) -> elements.add(new InjectMeta.InjectElement(value, true, key)));
        }
        injectMeta.setInjectElements(elements);
        injectMeta.setTargetClass(bean.getClass());
        return injectMeta;

    }


    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        SimpleAutowiredAnnotationBeanPostProcessor.beanFactory= (SimpleAutowiredCapableBeanFactory) simpleBeanFactory;
    }


    @Data
    public static class InjectFieldElement{
        private Member member;
        private boolean isField;
        private String elementName;

        public InjectFieldElement(Member member, boolean isField, String elementName) {
            this.member = member;
            this.isField = isField;
            this.elementName = elementName;
        }

        public void inject(Object target) throws Throwable {
            //递归获取bean
            if (SimpleAutowiredAnnotationBeanPostProcessor.beanFactory==null){
                throw new SimpleIOCBaseException("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory Exception! is null");
            }
            log.info("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory is:{}",SimpleAutowiredAnnotationBeanPostProcessor.beanFactory);
            Object dep = SimpleAutowiredAnnotationBeanPostProcessor.beanFactory.getBean(this.getElementName());
            Field field = (Field) this.getMember();
            ReflectUtils.makeAccessible(field);
            field.set(target,dep);

        }

    }


}

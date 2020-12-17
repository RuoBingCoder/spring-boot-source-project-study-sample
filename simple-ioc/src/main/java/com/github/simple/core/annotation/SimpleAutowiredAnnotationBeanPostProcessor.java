package com.github.simple.core.annotation;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.factory.SimpleBeanFactory;
import com.github.simple.core.factory.SimpleBeanFactoryAware;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.ReflectUtils;
import com.github.simple.core.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/12/12 1:56 下午
 * @description: SimpleAutowiredAnnotationBeanPostProcessor
 */
@Slf4j
@SimpleOrdered(-100)
public class SimpleAutowiredAnnotationBeanPostProcessor implements SimpleInstantiationAwareBeanPostProcessor, SimpleBeanFactoryAware {

    private static SimpleBeanFactory beanFactory;

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
        return getInjectMeta(fieldLinkedHashMap, bean);
    }

    private InjectMeta getInjectMeta(LinkedHashMap<String, Field> fields, Object bean) {
        InjectMeta injectMeta = new InjectMeta();
        Collection<InjectMeta.InjectElement> elements = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(fields)) {
            fields.forEach((key, value) -> elements.add(new InjectMeta.InjectElement(value, true, key)));
        }
        injectMeta.setInjectElements(elements);
        injectMeta.setTargetClass(bean.getClass());
        return injectMeta;

    }


    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        SimpleAutowiredAnnotationBeanPostProcessor.beanFactory = simpleBeanFactory;
    }


    @Data
    protected abstract static class InjectFieldElement {
        private Member member;
        private boolean isField;
        private String elementName;

        protected InjectFieldElement(Member member, boolean isField, String elementName) {
            this.member = member;
            this.isField = isField;
            this.elementName = elementName;
        }

        public void inject(Object target) throws Throwable {
            //递归获取bean
            if (beanFactory == null) {
                throw new SimpleIOCBaseException("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory Exception! is null");
            }

            log.info("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory is:{}", beanFactory);
            if (ReflectUtils.resolveDependencies(this.member)) {
                 log.info("====>>>>value 赋值开始<<<<<======");
                List<SimplePropertySource<Properties>> resource = beanFactory.getResource();
                String key = StringUtils.parsePlaceholder((Field) member);
                String value = (String) resource.get(0).getValue().get(key);
                Field field = (Field) this.getMember();
                ReflectUtils.makeAccessible(field);
                if (value == null) {
                    throw new SimpleIOCBaseException("no such field placeholder->" + key);
                }
                field.set(target, value);
                 log.info("====>>>>value 赋值结束<<<<<======");
                return;
            }
            Object dep = beanFactory.getBean(this.getElementName());
            if (dep != null) {
                Field field = (Field) this.getMember();
                ReflectUtils.makeAccessible(field);
                field.set(target, dep);
            }

        }


    }


}

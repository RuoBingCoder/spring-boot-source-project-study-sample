package com.github.simple.core.annotation;

import cn.hutool.core.collection.CollectionUtil;
import com.github.simple.core.beans.InjectFieldMetadataWrapper;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.beans.factory.SimpleBeanFactoryAware;
import com.github.simple.core.utils.AnnotationUtils;
import com.github.simple.core.utils.ReflectUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.*;

/**
 * @author: JianLei
 * @date: 2020/12/12 1:56 下午
 * @description: SimpleAutowiredAnnotationBeanPostProcessor
 * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry)
 */
@Slf4j
@SimpleOrdered(-100)
public class SimpleAutowiredAnnotationBeanPostProcessor implements SimpleInstantiationAwareBeanPostProcessor, SimpleBeanFactoryAware {

    private static SimpleBeanFactory beanFactory;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(4);

    public SimpleAutowiredAnnotationBeanPostProcessor() {
        autowiredAnnotationTypes.add(SimpleAutowired.class);
        autowiredAnnotationTypes.add(SimpleValue.class);
    }

    public void setAutowiredAnnotationType(Class<? extends Annotation> autowiredAnnotationType) {
        Assert.notNull(autowiredAnnotationType, "'autowiredAnnotationType' must not be null");
        this.autowiredAnnotationTypes.clear();
        this.autowiredAnnotationTypes.add(autowiredAnnotationType);
    }

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
        InjectMetadata injectMeta = findAutowiredMetadata(bean);
        injectMeta.inject(bean);

    }

    private InjectMetadata findAutowiredMetadata(Object bean) {
        List<InjectFieldMetadataWrapper> fieldWrapper = findAutowiredAnnotation(bean.getClass());
        return getInjectMeta(fieldWrapper, bean);
    }

    private List<InjectFieldMetadataWrapper> findAutowiredAnnotation(Class<?> aClass) {
        List<InjectFieldMetadataWrapper> injectFieldMetadataWrappers = new ArrayList<>();
        for (Class<? extends Annotation> annotationType : autowiredAnnotationTypes) {
            if (!AnnotationUtils.isCandidateClass(aClass, annotationType)) {
                return injectFieldMetadataWrappers;
            }
            LinkedHashMap<String, Field> autowiredAnnotation = ReflectUtils.findAutowiredAnnotation(aClass, annotationType);
            autowiredAnnotation.forEach((key, value) -> {
                InjectFieldMetadataWrapper metadataWrapper = InjectFieldMetadataWrapper.builder().fieldName(key).field(value).build();
                injectFieldMetadataWrappers.add(metadataWrapper);
            });
        }
        return injectFieldMetadataWrappers;
    }

    private InjectMetadata getInjectMeta(List<InjectFieldMetadataWrapper> fields, Object bean) {
        InjectMetadata injectMeta = new InjectMetadata();
        Collection<InjectMetadata.InjectElement> elements = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(fields)) {
            fields.forEach(f -> elements.add(new InjectMetadata.InjectElement(f.getField(), true, f.getFieldName())));
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
        private Class<?> fieldType;

        protected InjectFieldElement(Member member, boolean isField, String elementName) {
            this.member = member;
            this.isField = isField;
            this.elementName = elementName;
        }

        protected InjectFieldElement(Member member, boolean isField, String elementName, Class<?> fieldType) {
            this.member = member;
            this.isField = isField;
            this.elementName = elementName;
            this.fieldType = fieldType;
        }

        public void inject(Object target) throws Throwable {
            //递归获取bean
            if (beanFactory == null) {
                throw new SimpleIOCBaseException("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory Exception! is null");
            }

            log.info("SimpleAutowiredAnnotationBeanPostProcessor get beanFactory is:{}", beanFactory);
            if (this.getMember() == null) {
                return;
            }
            Field field = (Field) this.getMember();
            Object dep = beanFactory.resolveDependency(field, this.elementName);
            if (dep != null) {
                ReflectUtils.makeAccessible(field);
                field.set(target, dep);
            }

        }


    }


}

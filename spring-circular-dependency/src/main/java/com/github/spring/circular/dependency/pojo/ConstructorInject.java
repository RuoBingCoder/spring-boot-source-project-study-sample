package com.github.spring.circular.dependency.pojo;

import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/12/3 上午10:26
 * @description: spring 构造器注入原理;通过{@link org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#determineCandidateConstructors}解析依赖,⚠️:构造器注入是在
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBeanInstance 中 determineConstructorsFromBeanPostProcessors()方法调用开始检查构造器参数;最后调用方法步骤如下:
 * @see org.springframework.beans.factory.support.ConstructorResolver#createArgumentArray
 * @see org.springframework.beans.factory.support.ConstructorResolver#resolveAutowiredArgument
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#createOptionalDependency(DependencyDescriptor, String, Object...)
 */
@Component
public class ConstructorInject {

   /* private B b;

    @Autowired
    public ConstructorInject(B b) {
        this.b = b;
    }*/
}

package com.sjl.enable.annotation;

import lombok.Builder;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author: jianlei
 * @date: 2020/8/25
 * @description: SjlSanner
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(SjlSannerRegistry.class)
public @interface SjlSanner {

    Class<?>[] basePackagesClasses() default {} ;


    String[] value() default {};

    String[] basePackages() default {};

    Class<? extends Annotation> annotationClass() default Annotation.class;
}

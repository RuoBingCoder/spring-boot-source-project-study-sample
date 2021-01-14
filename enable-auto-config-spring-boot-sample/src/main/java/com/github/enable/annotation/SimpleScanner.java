package com.github.enable.annotation;

import org.springframework.context.annotation.Import;

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
@Import(SimpleScannerRegistry.class)
public @interface SimpleScanner {

    Class<?>[] basePackagesClasses() default {} ;


    String[] value() default {};

    String[] basePackages() default {};

    Class<? extends Annotation> annotationClass() default Annotation.class;
}

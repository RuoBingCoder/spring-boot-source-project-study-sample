package com.sjl.enable.annotation;

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
@Import(SjlScannerRegistry.class)
public @interface SjlScanner {

    Class<?>[] basePackagesClasses() default {} ;


    String[] value() default {};

    String[] basePackages() default {};

    Class<? extends Annotation> annotationClass() default Annotation.class;
}

package com.github.spring.components.learning.lighthttp.annotation;

import com.github.spring.components.learning.lighthttp.registry.LightHttpRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2021/2/25 10:51 上午
 * @description: LightHttpScan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LightHttpRegistry.class)
public @interface LightHttpScan {

    String[] basesPackages() default {};

}

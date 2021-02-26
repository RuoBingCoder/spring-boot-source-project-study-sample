package com.github.spring.components.learning.lighthttp.annotation;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2021/2/25 10:51 上午
 * @description: LightHttpScan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface LightHttpClient {


    String baseUrl();
}

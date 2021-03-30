package com.github.dubbo.provider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @see ServiceAnnotationBeanPostProcessor
 * first: registry @service mark bean ; second: registry ServiceBean ref= <p>@service mark bean</p>
 * create proxy
 */
@SpringBootApplication
public class DubboProviderBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderBootstrap.class)
                .run(args);
    }
}
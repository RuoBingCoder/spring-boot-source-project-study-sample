package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.context.SimpleEmbeddedValueResolverAware;
import com.github.simple.core.init.SimpleInitializingBean;
import com.github.simple.core.utils.SimpleStringValueResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 11:21 上午
 * @description: Init
 */
@SimpleComponent
@Slf4j
public class InitializingBeanTest implements SimpleInitializingBean, SimpleEmbeddedValueResolverAware {
    @Override
    public void afterPropertiesSet() {
        log.info("==>>Init method call back!");
    }

    @Override
    public void setEmbeddedValueResolver(SimpleStringValueResolver resolver) {
        String value = resolver.resolveStringValue("${app.name}");
        log.info("==>value :{}",value);
    }
}

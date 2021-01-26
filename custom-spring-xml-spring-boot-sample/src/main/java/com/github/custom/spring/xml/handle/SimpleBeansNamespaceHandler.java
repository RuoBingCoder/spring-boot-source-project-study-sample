package com.github.custom.spring.xml.handle;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author: JianLei
 * @date: 2020/9/10 3:24 下午
 * @description: 注册解析完的bean
 */

public class SimpleBeansNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        super.registerBeanDefinitionParser("registry",new SimpleBeansRegistry());
    }


}

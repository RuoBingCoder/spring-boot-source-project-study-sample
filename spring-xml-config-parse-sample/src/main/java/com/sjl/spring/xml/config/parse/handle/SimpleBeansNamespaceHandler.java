package com.sjl.spring.xml.config.parse.handle;

import com.sjl.spring.xml.config.parse.beans.CustomBean;
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

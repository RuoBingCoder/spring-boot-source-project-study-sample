package com.sjl.spring.xml.config.parse.handle;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author: JianLei
 * @date: 2020/9/10 3:33 下午
 * @description: 解析标签属性
 */
public class SimpleBeansRegistry implements BeanDefinitionParser {

  @SneakyThrows
  @Override
  public BeanDefinition parse(Element element, ParserContext parserContext) {
    String className = element.getAttribute("className");
    String id = element.getAttribute("id");
    String name = element.getAttribute("name");
    BeanDefinitionBuilder beanDefinitionBuilder = null;
    if (className != null && id != null && name != null) {
      Class<?> aClass = Class.forName(className);
      beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
      beanDefinitionBuilder.addPropertyValue("id", id);
      beanDefinitionBuilder.addPropertyValue("name", name);
      beanDefinitionBuilder.addPropertyValue("className", className);
      parserContext
          .getRegistry()
          .registerBeanDefinition(
              aClass.getSimpleName(), beanDefinitionBuilder.getBeanDefinition());
    }

    assert beanDefinitionBuilder != null;
    return beanDefinitionBuilder.getBeanDefinition();
  }
}

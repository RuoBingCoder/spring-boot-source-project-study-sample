package com.github.simple.core.definition;

import lombok.Builder;
import lombok.Data;

/**
 * @author: JianLei
 * @date: 2020/12/11 6:35 下午
 * @description: SimpleRootBeanDefinition
 */
@Data
@Builder
public class SimpleRootBeanDefinition {

    private Boolean isSingleton;

    private String beanName;

    private Class<?> rootClass;

    private String alias;

}

package com.sjl.custom.aop.bean;

import lombok.Data;

import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/22 5:18 下午
 * @description:
 */
@Data
@Deprecated
public class AspectHolder<T> {

    private Class<?> clazz;
    private List<T> configAttribute;

}

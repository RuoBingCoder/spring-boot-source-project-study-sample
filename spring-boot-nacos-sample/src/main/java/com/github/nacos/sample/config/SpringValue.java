package com.github.nacos.sample.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * @author jianlei.shi
 * @date 2021/2/19 8:21 下午
 * @description SpringValue
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpringValue {
    private String beanName;
    private Field field;
    private WeakReference<Object> beanRef;


}

package com.github.custom.aop.bean;

import lombok.Data;

/**
 * @author: JianLei
 * @date: 2020/9/25 6:04 下午
 * @description:
 */
@Data
public class AopConfig {
    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private Class<?> aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}

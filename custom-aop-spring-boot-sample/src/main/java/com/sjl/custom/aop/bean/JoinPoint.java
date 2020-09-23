package com.sjl.custom.aop.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author: JianLei
 * @date: 2020/9/22 4:47 下午
 * @description:
 */
@Data
public class JoinPoint {
    private String params;
    private Class<?> clazz;
    private String methodName;


}

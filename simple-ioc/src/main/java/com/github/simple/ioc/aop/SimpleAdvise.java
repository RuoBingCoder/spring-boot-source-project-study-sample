package com.github.simple.ioc.aop;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/15 7:28 下午
 * @description: Advise
 */
public interface SimpleAdvise {

    Method getMethod(String annotation);

    Object getAspectObject();

    String getPointCut();

    List<SimpleAdviseSupport.MethodWrapper> getAllAspectMethods();

}

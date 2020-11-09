package com.sjl.spring.components.transaction.service;

import com.sjl.spring.components.transaction.custom.annotation.EasyTransactional;

/**
 * @author: JianLei
 * @date: 2020/11/7 2:11 下午
 * @description: CommonOperateService
 */
public interface CommonOperateService<T,S> {

     Integer operation(T t,S s);


}

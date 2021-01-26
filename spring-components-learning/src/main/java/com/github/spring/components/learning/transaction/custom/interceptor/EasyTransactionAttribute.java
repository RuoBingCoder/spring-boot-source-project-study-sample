package com.github.spring.components.learning.transaction.custom.interceptor;

import com.github.spring.components.learning.transaction.service.impl.HeroServiceImpl;

/**
 * @author: JianLei
 * @date: 2020/11/8 1:36 下午
 * @description: EasyTransactionAttribute
 * @see HeroServiceImpl
 */
public interface EasyTransactionAttribute extends EasyTransactionDefinition{

    /**
     * @Author jianlei.shi
     * @Description 异常回滚
     * @Date 1:37 下午 2020/11/8
     * @Param
     * @return
     **/
    boolean rollbackOn(Throwable ex);

}

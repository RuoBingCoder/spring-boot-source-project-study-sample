package com.github.spring.components.transaction.custom.interceptor;

/**
 * @author: JianLei
 * @date: 2020/11/8 1:36 下午
 * @description: EasyTransactionAttribute
 * @see com.github.spring.components.transaction.service.impl.HeroServiceImpl
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

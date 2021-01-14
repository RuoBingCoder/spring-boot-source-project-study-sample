package com.github.spring.components.transaction.custom.interceptor;

/**
 * @author: JianLei
 * @date: 2020/11/8 1:36 下午
 * @description: EasyTransactionAttribute
 */
public interface EasyTransactionAttribute {

    /**
     * @Author jianlei.shi
     * @Description 异常回滚
     * @Date 1:37 下午 2020/11/8
     * @Param
     * @return
     **/
    boolean rollbackOn(Throwable ex);

}

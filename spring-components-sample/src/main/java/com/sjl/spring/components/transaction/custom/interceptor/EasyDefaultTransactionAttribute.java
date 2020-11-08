package com.sjl.spring.components.transaction.custom.interceptor;

/**
 * @author: JianLei
 * @date: 2020/11/8 1:43 下午
 * @description: EasyDefaultTransactionAttribute
 */

public class EasyDefaultTransactionAttribute implements EasyTransactionAttribute{
    @Override
    public boolean rollbackOn(Throwable ex) {
        return ex instanceof RuntimeException || ex instanceof Error;
    }
}

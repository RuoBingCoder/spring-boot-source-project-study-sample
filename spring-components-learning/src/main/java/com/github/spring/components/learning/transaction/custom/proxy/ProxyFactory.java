package com.github.spring.components.learning.transaction.custom.proxy;


import com.github.spring.components.learning.transaction.custom.suuport.TransactionAdviserSupport;

import java.lang.reflect.Proxy;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:40 上午
 * @description: ProxyFactory
 */

public class ProxyFactory implements EasyProxy{

    private TransactionAdviserSupport support;

    public ProxyFactory(TransactionAdviserSupport support) {
        this.support = support;
    }

    @Override
    public  <T> T createProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(support.getClazz().getClassLoader()
                ,new Class[]{support.getClazz().getInterfaces()[0]},new JdkTransactionInvocation());
    }

   public static ProxyFactory newProxyFactory(TransactionAdviserSupport support){
        return new ProxyFactory(support);
    }
}

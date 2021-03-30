package com.github.spring.dependency.inject.demo.proxy;


import com.github.factory.AbsProxyFactory;

/**
 * @author jianlei.shi
 * @date 2021/3/10 5:52 下午
 * @description TestProxyFactory
 */

public class TestProxyFactory extends AbsProxyFactory {
    @Override
    protected <T> T getHandler() {
        return (T) new JdkDynamicHandler();
    }


}

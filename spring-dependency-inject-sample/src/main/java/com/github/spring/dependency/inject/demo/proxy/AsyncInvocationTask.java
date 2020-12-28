package com.github.spring.dependency.inject.demo.proxy;

import com.github.spring.dependency.inject.demo.bean.MyAsyncHolder;
import com.github.spring.dependency.inject.demo.utils.ThreadPoolUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: JianLei
 * @date: 2020/9/28 3:27 下午
 * @description: AsyncInvocationTask
 */

public class AsyncInvocationTask implements MyInvocation<MyAsyncHolder>{

    private final Method method;
    private final Object[] args;

    public AsyncInvocationTask(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public boolean invoke(MyAsyncHolder myAsyncHolder) throws IllegalAccessException, InstantiationException {
        synchronized (this) {
            Object proxyBean = myAsyncHolder.getBean().newInstance();
            AtomicBoolean flag = new AtomicBoolean(false);
            for (Method method1 : myAsyncHolder.getMethods()) {
                if (method1.getName().equals(method.getName())
                        && method1.getParameterTypes().length == method.getParameterTypes().length) {
                    ThreadPoolUtil.executeTask(
                            () -> {
                                try {
                                    method.invoke(proxyBean, args);
                                    flag.set(true);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            });
                }
                if (flag.get()){
                   return true;

                }
            }
        }
        return false;
    }
}

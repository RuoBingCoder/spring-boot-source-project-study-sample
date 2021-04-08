package com.github.spring.components.lightweight.test.sample.jvm.handle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author jianlei.shi
 * @date 2021/4/1 10:31 上午
 * @description MethodHandleDemo
 * <a href="https://juejin.cn/book/6844733778389106702/section/6844733778569478158"/>
 */

public class MethodHandleDemo {

    public static void main(String[] args) {
        Foo foo = new Foo();

        MethodType methodType = MethodType.methodType(void.class, String.class);
        try {
            MethodHandle methodHandle = MethodHandles.lookup().findVirtual(Foo.class, "print", methodType);
            methodHandle.invokeExact(foo, "world");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}

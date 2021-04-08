package com.github.spring.components.lightweight.test.sample.jvm.handle;

/**
 * @author jianlei.shi
 * @date 2021/4/1 10:31 上午
 * @description Foo
 */

public class Foo {

    /**
     * 添加
     * iload 局部变量区->操作数栈
     * istore 操作数栈->局部变量区
     * @return
     * @author jianlei.shi
     * @date 2021-04-01 10:54:28
     */
    public void add() {
        int i = 0;
        i = ++i + i++ + i++ + i++;
        System.out.println("i=" + i);
    }

    public void print(String s) {
        System.out.println("hello, " + s);
    }

    public static void main(String[] args) {
        Foo f=new Foo();
        f.add();
    }

}

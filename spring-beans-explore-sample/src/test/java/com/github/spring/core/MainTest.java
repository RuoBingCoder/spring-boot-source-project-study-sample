package com.github.spring.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianlei.shi
 * @date 2021/1/5 3:49 下午
 * @description MainTest
 */

public class MainTest {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add(0,"1");
        list.forEach(i-> System.out.println("i:"+i));
    }
}

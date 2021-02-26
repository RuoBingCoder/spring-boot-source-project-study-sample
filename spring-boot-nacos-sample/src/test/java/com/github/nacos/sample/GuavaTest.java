package com.github.nacos.sample;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.lang.reflect.Field;

/**
 * @author jianlei.shi
 * @date 2021/2/22 3:23 下午
 * @description GuavaTest
 */

public class GuavaTest {
    private String value="a";

    public static void put(){
        Multimap<Integer, String> NAMESPACE_NAMES = LinkedHashMultimap.create();
        NAMESPACE_NAMES.put(1,"a");
        NAMESPACE_NAMES.put(1,"b");
        NAMESPACE_NAMES.put(1,"c");
        System.out.println(NAMESPACE_NAMES.get(1).toString());

    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        put();
        final GuavaTest guavaTest = new GuavaTest();
        Temp temp=new Temp();
        temp.setV1(guavaTest.value);
        System.out.println("temp old : "+temp.toString());
        final Field field = guavaTest.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(guavaTest,"b");
        System.out.println("temp new : "+temp.toString());

    }
}

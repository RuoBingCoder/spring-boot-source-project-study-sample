package com.github.spring.components.learning;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/3/11 11:41 上午
 * @description Trans
 */

public class Trans {

    private String name;
    private Map<String, String> cacheStringStringMap = new HashMap<>();

    public Trans() {
    }

    public Trans(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Trans trans=new Trans();
        trans.cacheStringStringMap.put("a","12");
        Trans trans2=new Trans();
        final String a = trans2.cacheStringStringMap.get("a");
        System.out.println(a);
    }
}

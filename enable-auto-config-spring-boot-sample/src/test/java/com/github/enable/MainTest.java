package com.github.enable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/11/24 下午4:18
 * @description: MainTest
 */

public class MainTest {

    public static void main(String[] args) {
        Map<String, String> orginalMap=new HashMap<>();
        orginalMap.put("1","one");
        orginalMap.put("2","two");
        orginalMap.put("3","three");
        List<String> keyList=new ArrayList<>(orginalMap.keySet());
        List<String> valueList=new ArrayList<>(orginalMap.values());
        System.out.println("==>"+keyList.toString().replace("[","【").replace("]","】"));
        System.out.println("==>"+valueList.toString());
    }
}

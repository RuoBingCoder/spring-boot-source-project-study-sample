package com.sjl.bean.life.cycle.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: JianLei
 * @date: 2020/9/11 4:51 下午
 * @description:
 */

public class Constant {

    public static final AtomicInteger count=new AtomicInteger(0);

    public static final Map<String, Object> beansMap=new HashMap<>();
}

package com.sjl.es.spring.boot.sample.utils;

import cn.hutool.core.lang.Assert;

/**
 * @author: JianLei
 * @date: 2020/11/16 下午7:43
 * @description: StringUtil
 */

public class StringUtil {

    public static String[] parseHost(String host){
        Assert.notNull(host,"host notNull!");
        return host.split(":");

    }
}

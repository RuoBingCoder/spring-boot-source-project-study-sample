package com.sjl.boot.autowired.inject.sample.utils;

/**
 * @author: JianLei
 * @date: 2020/9/28 11:32 上午
 * @description: StringUtil
 */

public class StringUtil {

    /**
     * 将类名首字母小写
     * @param str
     * @return
     */
    public static String lowerFirstChar(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

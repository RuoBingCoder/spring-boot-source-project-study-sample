package com.github.spring.components.utils;

import com.google.common.base.CaseFormat;

/**
 * @author: jianlei.shi
 * @date: 2020/12/26 10:45 下午
 * @description: StringUtils
 */

public class StringUtils {

    public static String lowerUnderscore(String source) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, source);
    }

    public static String formatPlaceholder(String source) {
        String trim = source.trim();
        char[] chars = trim.toCharArray();
        String res=null;
        for (int i = chars.length-1; i >=0; i--) {
            if (chars[i]==','){
                chars[i]='-';
                res=new String(chars);
                break;
            }

        }
        assert res != null;
        return res.replace("-", "");
    }

    public static void main(String[] args) {
        String str = "<if> (12,34,56,</if>";
        System.out.println(formatPlaceholder(str));
    }
}

package com.github.spring.components.learning.utils;

import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jianlei.shi
 * @date 2020/12/29 7:31 下午
 * @description DateUtils
 */

public class DateUtils {

    public static String getCurrentTime() {
        // 小写的hh取得12小时，大写的HH取的是24小时
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }

    public static String getNow(){
        return DateUtil.now();
    }

}

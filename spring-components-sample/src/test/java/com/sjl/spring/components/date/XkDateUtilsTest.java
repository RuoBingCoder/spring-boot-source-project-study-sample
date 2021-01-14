package com.sjl.spring.components.date;

import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import com.xkzhangsan.time.formatter.DateTimeFormatterUtil;

import java.time.Instant;
import java.util.Date;

import static com.xkzhangsan.time.formatter.DateTimeFormatterUtil.YYYY_MM_DD_HH_MM_SS_FMT;

/**
 * @author jianlei.shi
 * @date 2021/1/7 11:48 上午
 * @description XkDateUtilsTest
 */

public class XkDateUtilsTest {

    public static void main(String[] args) {
        Date date = DateTimeConverterUtil.toDate(Instant.now());
        System.out.println(date);
        System.out.println(DateTimeFormatterUtil.format(new Date(), YYYY_MM_DD_HH_MM_SS_FMT));

    }
}

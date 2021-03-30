package com.github.simple.core.utils;

import com.github.simple.core.exception.SimpleTypeConvertException;
import lombok.extern.slf4j.Slf4j;

/**
 * 类型转换
 *
 * @author
 * @author: jianlei.shi
 * @date: 2020/12/20 4:41 下午
 * @description: TypeConvertUtils
 * @date
 */
@Slf4j
public class TypeConvertUtils {

    public static <T> Object convert(Class<T> type, String value) {
        if (value == null || "".equals(value)) {
            throw new SimpleTypeConvertException("value is null");

        }
        if (type.equals(Integer.class)) {
            int res;
            try {
                res = Integer.parseInt(value);
            } catch (Exception e) {
                log.error("["+value + "]is not convert Integer",e);
                throw new SimpleTypeConvertException("["+value + "] is not convert Integer");

            }

            return res;
        }
        if (type.equals(String.class)) {
            return value;
        }
        if (type.equals(Long.class)) {
            long res;
            try {
                res = Long.parseLong(value);
            } catch (Exception e) {
                log.error("["+value + "]is not convert Long",e);
                throw new SimpleTypeConvertException("["+value + "]  is not convert Long");

            }

            return  res;
        }
        return null;

    }
}

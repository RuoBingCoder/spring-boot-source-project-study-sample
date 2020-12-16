package com.github.simple.core.utils;

import com.github.simple.core.exception.SimplePropertiesLoadException;
import com.github.simple.core.resource.SimpleResource;

import java.util.Properties;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:20 下午
 * @description: PropertyUtils
 */

public class PropertyUtils {


    public static Properties load(SimpleResource resource) {
        try {
            Properties props = new Properties();
            props.load(resource.getInputStream());
            return props;
        } catch (Exception e) {
                throw new SimplePropertiesLoadException("Properties load exception"+e.getMessage());
        }

    }
}

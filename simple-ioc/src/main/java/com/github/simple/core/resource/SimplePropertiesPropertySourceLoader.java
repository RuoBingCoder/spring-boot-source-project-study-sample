package com.github.simple.core.resource;

import com.github.simple.core.utils.PropertyUtils;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:18 下午
 * @description: SimplePropertiesPropertySourceLoader
 */

public class SimplePropertiesPropertySourceLoader {

    public List<SimplePropertySource<Properties>> load(String fileName, SimpleResource resource){
        Properties properties = PropertyUtils.load(resource);
        return Collections.singletonList(new SimplePropertySource<>(fileName, properties));
    }
}

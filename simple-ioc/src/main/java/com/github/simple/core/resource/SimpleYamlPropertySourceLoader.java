package com.github.simple.core.resource;

import com.github.simple.core.utils.YamlUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/1/14 4:33 下午
 * @description SimpleYamlPropertySourceLoader
 */

public class SimpleYamlPropertySourceLoader implements SimpleSourceLoader {
    @Override
    public List<SimplePropertySource<Map<String, Object>>>  load(String fileName, SimpleResource resource) throws IOException {
        Map<String, Object> objectMap = YamlUtils.loadForIs(resource.getInputStream());
        return Collections.singletonList(new SimplePropertySource<>(fileName, objectMap));
    }
}

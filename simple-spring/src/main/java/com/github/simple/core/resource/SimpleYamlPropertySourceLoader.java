package com.github.simple.core.resource;

import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.utils.YamlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/1/14 4:33 下午
 * @description SimpleYamlPropertySourceLoader
 */
@Slf4j
public class SimpleYamlPropertySourceLoader implements SimpleSourceLoader {
    @Override
    public List<SimplePropertySource<Map<String, Object>>>  load(String fileName, SimpleResource resource) {
         try {

             Map<String, Object> objectMap = YamlUtils.loadForIs(resource.getInputStream());
             return Collections.singletonList(new SimplePropertySource<>(fileName, objectMap));
         } catch (Exception e) {
             log.error("SimpleYamlPropertySourceLoader load error",e);
             throw new SimpleIOCBaseException("SimpleYamlPropertySourceLoader load error->"+e.getMessage());
         }
    }


}

package com.github.simple.core.env;

import com.github.simple.core.beans.aware.SimpleBeanFactoryAware;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.utils.YamlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author jianlei.shi
 * @date 2021/2/14 9:19 下午
 * @description SimpleStandardEnvironment
 */
@Slf4j
public class SimpleStandardEnvironment implements SimpleEnvironment, SimpleBeanFactoryAware {

    public static final String SIMPLE_STANDARD_ENVIRONMENT_BEAN_NAME="simpleStandardEnvironment";
    private SimpleBeanFactory simpleBeanFactory;

    @Override
    public SimpleMutablePropertySources getPropertySources() {
         try {

             return simpleBeanFactory.getBean(SimpleMutablePropertySources.SIMPLE_MUTABLE_PROPERTY_SOURCES_BEAN_NAME);
         } catch (Throwable e) {
             log.error("SimpleStandardEnvironment getPropertySources error",e);
             throw new SimpleIOCBaseException("SimpleStandardEnvironment getPropertySources error");
         }
    }

    @Override
    public String getProperty(String key) {
         try {
             return getProperty(key, getPropertySources());
         } catch (Throwable ex) {
            log.error("getProperty error! ",ex);
            throw new SimpleIOCBaseException("getProperty error! msg :"+ex.getMessage());
         }
    }



    public String getProperty(String key, SimpleMutablePropertySources source) {
        if (source == null) {
            throw new SimpleIOCBaseException("SimpleStandardEnvironment getPropertySources is null");
        }
        final List<SimplePropertySource<?>> simplePropertySources = source.getSimplePropertySources();
        for (SimplePropertySource<?> simplePropertySource : simplePropertySources) {
            final Object value = simplePropertySource.getValue();
            if (value instanceof Properties) {
                Properties properties = (Properties) value;
                return properties.getProperty(key);
            }
            if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                return YamlUtils.getProperty(key, map);
            }
        }
        throw new SimpleIOCBaseException("no such key->" + "[" + key + "]" + "  value!");
    }

    @Override
    public void setBeanFactory(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }
}

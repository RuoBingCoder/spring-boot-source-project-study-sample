package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.env.SimpleEnvironment;
import com.github.simple.core.env.SimpleEnvironmentPostProcessor;
import com.github.simple.core.env.SimpleMutablePropertySources;
import com.github.simple.core.resource.SimpleClassPathResource;
import com.github.simple.core.resource.SimplePropertySource;
import com.github.simple.core.resource.SimpleYamlPropertySourceLoader;

import java.util.List;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/2/14 9:46 下午
 * @description SimpleEnvironmentPostProcessorImpl
 */
@SimpleComponent
public class SimpleEnvironmentPostProcessorImpl implements SimpleEnvironmentPostProcessor {


    @Override
    public void postProcessEnvironment(SimpleEnvironment environment) {
        final SimpleMutablePropertySources propertySources = environment.getPropertySources();
        SimpleClassPathResource pr = new SimpleClassPathResource("application-test");
        SimpleYamlPropertySourceLoader loader = new SimpleYamlPropertySourceLoader();
        List<SimplePropertySource<Map<String, Object>>> propertySourceList = loader.load(pr.getFilename(), pr);
        propertySources.addLast(propertySourceList.get(0));

    }
}

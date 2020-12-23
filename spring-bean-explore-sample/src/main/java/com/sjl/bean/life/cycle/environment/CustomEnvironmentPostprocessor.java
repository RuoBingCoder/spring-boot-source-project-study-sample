package com.sjl.bean.life.cycle.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/22 下午1:38
 * @description: CustomEnvironmentPostprocessor
 */
@Slf4j
public class CustomEnvironmentPostprocessor implements EnvironmentPostProcessor {
    PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        ClassPathResource classPathResource = new ClassPathResource("env-application.properties");
        try {
            log.info("------->>>>begin load custom  properties!<<<<<---------------");
            PropertySource<?> propertySource = loader.load("EnvApplicationYaml", classPathResource).get(0);
            propertySources.addFirst(propertySource);
        } catch (Exception e) {
            log.error("add propertySource error!", e);
        }
    }
}

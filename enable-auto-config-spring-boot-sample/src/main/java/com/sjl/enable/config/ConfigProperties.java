package com.sjl.enable.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jianlei.shi
 * @date 2020/12/31 2:06 下午
 * @description ConfigProperties
 * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder#bind(ConfigurationPropertiesBean)
 */
@Configuration
@ConfigurationProperties(prefix = "com.github")
@Data
public class ConfigProperties {
    
    private String info;
    
    private String address;
    
    private String appName;
}

package com.github.nacos.sample.config.service;

import com.github.nacos.sample.config.Config;
import com.github.nacos.sample.config.DefaultConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/2/19 7:10 下午
 * @description ConfigService
 */
@Component
@Slf4j
public class ConfigService {


   public static Config getConfig(ConfigurableListableBeanFactory beanFactory, String nameSpace){
      final DefaultConfig config = (DefaultConfig) beanFactory.getBean(Config.class);
      return config;
   }
}

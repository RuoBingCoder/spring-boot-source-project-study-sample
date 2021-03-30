package com.github.simple.demo.test;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.annotation.SimpleValue;
import com.github.simple.core.env.SimpleEnvironment;
import com.github.simple.core.env.aware.SimpleEnvironmentAware;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2021/2/14 10:58 下午
 * @description EnvTest
 */
@SimpleComponent
@Slf4j
public class EnvTest implements SimpleEnvironmentAware {

    @SimpleValue("${simple.app.name}")
    private String appName;

    @Override
    public void setEnv(SimpleEnvironment environment) {
        log.info("get env info: {}",environment.getProperty("app.name"));
        log.info("get env info: {}",environment.getProperty("app.name"));
        log.info("get simple app name : {}",appName==null?"null":appName);

    }
}

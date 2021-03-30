package com.github.simple.core.env.aware;

import com.github.simple.core.env.SimpleEnvironment;

/**
 * @author jianlei.shi
 * @date 2021/2/14 10:03 下午
 * @description: SimpleEnvAware
 */
public interface SimpleEnvironmentAware {

    void setEnv(SimpleEnvironment environment);
}

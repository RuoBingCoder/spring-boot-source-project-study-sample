package com.github.simple.core.env;

/**
 * @author jianlei.shi
 * @date 2021/2/14 9:05 下午
 * @description: SimpleEnvironment
 */
public interface SimpleEnvironment extends SimplePropertyResolver {

    SimpleMutablePropertySources getPropertySources() ;
}

package com.github.simple.core.resource;

import java.io.IOException;

/**
 * @author jianlei.shi
 * @date 2021/1/14 4:31 下午
 * @description: 配置文件源
 */
public interface SimpleSourceLoader {

    <T> T load(String fileName, SimpleResource resource) throws IOException;
}

package com.github.simple.core.env;

import com.github.simple.core.resource.SimplePropertySource;

import java.util.List;

/**
 * @author jianlei.shi
 * @date 2021/2/14 9:10 下午
 * @description: SimplePropertySources
 */
public interface SimplePropertySources {

   <T> void addFirst(SimplePropertySource<T> simplePropertySource);


   <T> void addLast(SimplePropertySource<T> simplePropertySource);

   List<SimplePropertySource<?>>  getSimplePropertySources();

}

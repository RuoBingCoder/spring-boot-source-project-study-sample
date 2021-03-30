package com.github.simple.core.resource;

import org.springframework.core.io.InputStreamSource;

/**
 * @author: JianLei
 * @date: 2020/12/16 7:12 下午
 * @description: SimpleResource
 */
public interface SimpleResource extends InputStreamSource {


    String getFilename();

    Boolean fileAttribute();

}

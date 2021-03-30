package com.github.simple.spi;

import com.github.simple.core.annotation.SimpleBean;
import com.github.simple.core.annotation.SimpleConfig;
import com.github.simple.core.annotation.SimpleValue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:57 下午
 * @description: test
 * {@link SimpleConfig}
 * {@link SimpleBean}
 */
@SimpleConfig
@Slf4j
public class EsSearchConfig {

    @SimpleValue("${simple.address}")
    private String address;

    @SimpleBean
    public  EsHolder esHolder() {
        return new EsHolder(address);
    }


}

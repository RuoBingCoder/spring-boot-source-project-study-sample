package com.github.spi.service.impl;

import com.github.spi.annotation.ServiceName;
import com.github.spi.service.Invoke;

/**
 * @author: JianLei
 * @date: 2020/8/26 7:51 下午
 * @description:
 */
@ServiceName(value = "DubboInvokeImpl实现类")
public class DubboInvokeImpl  implements Invoke {
    @Override
    public String invoke(String ip) {
        return "this is dubbo invoke:"+ip;
    }
}

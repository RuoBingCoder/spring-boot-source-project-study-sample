package com.sjl.spi.service.impl;

import com.sjl.spi.service.Invoke;

/**
 * @author: JianLei
 * @date: 2020/8/26 7:51 下午
 * @description:
 */

public class DubboInvokeImpl  implements Invoke {
    @Override
    public String invoke(String ip) {
        return "this is dubbo invoke:"+ip;
    }
}

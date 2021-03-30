package com.github.dubbo;

import com.github.dubbo.pojo.LayoutQueryParam;

/**
 * 演示服务
 *
 * @author shijianlei
 * @date 2021-03-08 10:29:56
 */
public interface DemoService {

    String sayHello(String name);

    String query(LayoutQueryParam queryParam);

}
package com.github.dubbo.provider;

import com.github.dubbo.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author jianlei.shi
 * @date 2021/3/22 6:51 下午
 * @description HelloServiceImpl
 */
@Service(version = "1.0.0")
public class HelloServiceImpl implements HelloService {
    @Override
    public void sawHello() {
        System.out.println("hello word");
    }
}

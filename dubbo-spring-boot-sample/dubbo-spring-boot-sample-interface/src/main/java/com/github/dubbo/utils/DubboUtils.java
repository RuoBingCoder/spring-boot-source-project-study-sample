package com.github.dubbo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.Collections;

/**
 * @author jianlei.shi
 * @date 2021/3/17 11:48 上午
 * @description DubboUtils
 */
@Slf4j
public class DubboUtils {


    public static <T> T invoke(Class<T> intefaces, String version, Integer timeout, String address) {
        ApplicationConfig actionConfig = new ApplicationConfig();
        actionConfig.setName("dubbo-auto-configure-consumer-sample"); //确保唯一
        RegistryConfig rc = new RegistryConfig();
        rc.setAddress(address);
        rc.setTimeout(timeout);
        ReferenceConfig<Object> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setTimeout(timeout);
        referenceConfig.setRegistries(Collections.singletonList(rc));
        referenceConfig.setApplication(actionConfig);
        referenceConfig.setInterface(intefaces);
        referenceConfig.setRetries(3);
        referenceConfig.setVersion(version);
        try {

            final Object result = referenceConfig.get();
            return (T) result;
        } catch (Exception e) {
            log.error("调用RPC异常", e);
            throw new RuntimeException("调用RPC异常", e);
        }
    }
}

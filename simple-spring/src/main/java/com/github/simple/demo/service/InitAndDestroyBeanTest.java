package com.github.simple.demo.service;

import com.github.simple.core.annotation.SimpleComponent;
import com.github.simple.core.context.aware.SimpleEmbeddedValueResolverAware;
import com.github.simple.core.disposable.SimpleDisposableBean;
import com.github.simple.core.init.SimpleInitializingBean;
import com.github.simple.core.utils.SimpleStringValueResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 11:21 上午
 * @description: Init
 */
@SimpleComponent
@Slf4j
public class InitAndDestroyBeanTest implements SimpleInitializingBean, SimpleEmbeddedValueResolverAware , SimpleDisposableBean {
    @Override
    public void afterPropertiesSet() {
        log.info("==>>Init method call back!");
    }

    @Override
    public void setEmbeddedValueResolver(SimpleStringValueResolver resolver) throws Throwable {
        String value = resolver.resolveStringValue("${app.name}");
        log.info("==>value :{}",value);
    }

    @Override
    public void destroy() {
        log.info("##################################<InitAndDestroyBeanTest> destroy bean##################################");
    }

    public static void main(String[] args) {
        String str="选项1手机卡换房间卡舒服哈接口费哈手机客服哈交罚款哈手机壳和繁花似锦复活节萨哈手机号反对司法解释将阿克琉斯就会发觉阿奎罗发哈看哈放假啊啥地方故事的结局阿斯科利就尬时光孤独就是感觉阿拉山口建行卡里都是尬";
        System.out.println(str.length());
    }
}

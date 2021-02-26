package com.github.nacos.sample.support;

import com.github.nacos.sample.config.SpringValue;
import com.github.nacos.sample.config.listener.AutoUpdateConfigChangeListener;
import helper.ThreadPoolHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/2/22 4:25 下午
 * @description ThreadPoolValuePostProcessor
 */
@Component
public class ThreadPoolValuePostProcessor extends AutoUpdateConfigChangeListener implements BeanPostProcessor {

    protected ThreadPoolValuePostProcessor(ConfigurableEnvironment environment, ConfigurableBeanFactory beanFactory) {
        super(environment, beanFactory);
    }

    public ThreadPoolValuePostProcessor() {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ThreadPoolHelper){
            ThreadPoolHelper threadPoolHelper= (ThreadPoolHelper) bean;
            targetValueMap.put(beanName,getSpringValue(threadPoolHelper,beanName));

        }
        return null;
    }


    @Override
    protected Map<String, SpringValue> getValues() {

        return null;
    }
}

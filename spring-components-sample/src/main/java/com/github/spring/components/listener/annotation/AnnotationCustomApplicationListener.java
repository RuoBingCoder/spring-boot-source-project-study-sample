package com.github.spring.components.listener.annotation;

import com.alibaba.fastjson.JSONObject;
import com.github.spring.components.event.CustomEvent;
import com.github.spring.components.event.CustomTwoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/4 10:48 上午
 * @description: AnnotationApplicationCustomListener
 * @see org.springframework.context.event.EventListenerMethodProcessor#afterSingletonsInstantiated
 * <code>
 * ApplicationListener<?> applicationListener =
 * factory.createApplicationListener(beanName, targetType, methodToUse);
 * 注解式创建ApplicationListener
 * </code>
 * @see DefaultListableBeanFactory#preInstantiateSingletons() -> smartSingleton.afterSingletonsInstantiated();
 */
@Component
@Order(-2)
@Slf4j
public class AnnotationCustomApplicationListener {


    @EventListener
    public CustomTwoEvent listener(CustomEvent customEvent) {
        log.error("=============================开始===========================================");
        log.info("==>this is annotation event!:{}", JSONObject.toJSONString(customEvent.getSource()));
        return new CustomTwoEvent(this, JSONObject.toJSONString(customEvent));
    }
}

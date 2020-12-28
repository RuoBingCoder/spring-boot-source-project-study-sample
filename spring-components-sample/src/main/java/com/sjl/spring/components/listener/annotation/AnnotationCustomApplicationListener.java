package com.sjl.spring.components.listener.annotation;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.components.event.CustomEvent;
import com.sjl.spring.components.event.CustomTwoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/4 10:48 上午
 * @description: AnnotationApplicationCustomListener
 */
@Component
@Order(-2)
@Slf4j
public class AnnotationCustomApplicationListener {


    @EventListener
    public CustomTwoEvent listener(CustomEvent customEvent){
        log.error("=============================开始===========================================");
        log.info("==>this is annotation event!:{}", JSONObject.toJSONString(customEvent.getSource()));
        return new CustomTwoEvent(this,JSONObject.toJSONString(customEvent));
    }
}

package com.github.spring.components.listener.annotation;

import com.github.spring.components.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.NioEndpoint;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/11/4 11:25 上午
 * @description: RefreshContextListener
 */
@Component
@Slf4j
public class RefreshContextListener {


    @EventListener(classes = ContextRefreshedEvent.class )
    public void listener(ApplicationEvent event){
        NioEndpoint nioEndpoint=new NioEndpoint();
        SpringUtil.tomcatBean.put(NioEndpoint.class,nioEndpoint);
//        System.out.println("===>"+super.setProperty());
        log.info("-->>ContextRefreshedEvent listener start!:{}", event.getSource().toString());

    }
}

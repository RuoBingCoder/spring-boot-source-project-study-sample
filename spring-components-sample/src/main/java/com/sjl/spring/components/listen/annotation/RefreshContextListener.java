package com.sjl.spring.components.listen.annotation;

import lombok.extern.slf4j.Slf4j;
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

    /**
     * @Author jianlei.shi
     * @Description EventListener 注解主要有两个属性：classes 和 condition。
     * classes 表示所需要侦听的事件类型，是个数组，所以允许在单个方法里进行多个不同事件的侦听，以此做到复用的效果；
     * condition 顾名思义就是用来定义所侦听事件是否处理的前置条件，这里需要注意的是使用 Spring Expression Language （SpEL）定义条件，比如 #root.event 表示了具体的 ApplicationEvent对象,
     * @Date 11:30 上午 2020/11/4
     * @Param
     * @return
     **/
    @EventListener(classes = ContextRefreshedEvent.class )
    public void listener(ApplicationEvent event){
        log.info("-->>ContextRefreshedEvent listener start!:{}", event.getSource().toString());

    }
}

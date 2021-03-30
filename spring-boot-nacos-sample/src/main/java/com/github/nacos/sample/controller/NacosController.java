package com.github.nacos.sample.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.client.config.impl.CacheData;
import com.alibaba.nacos.client.config.impl.ClientWorker;
import com.alibaba.nacos.spring.context.event.config.NacosConfigReceivedEvent;
import com.github.http.ModelResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Properties;

/**
 * @author jianlei.shi
 * @date 2021/2/13 2:57 下午
 * @description NacosController
 * @see CacheableEventPublishingNacosServiceFactory
 */
@RequestMapping("/nacos")
@RestController
public class NacosController {
    /**
     *event
     * @see com.alibaba.nacos.spring.context.event.config.DelegatingEventPublishingListener#publishEvent(String)
     * 查找NacosValue 值 从远程获取
     * @see com.alibaba.nacos.spring.core.env.NacosPropertySourcePostProcessor#buildNacosPropertySources(String, BeanDefinition) 
     * @see com.alibaba.nacos.spring.core.env.AbstractNacosPropertySourceBuilder#build(String, BeanDefinition)  
     * @see com.alibaba.nacos.spring.core.env.AbstractNacosPropertySourceBuilder#doBuild(String, BeanDefinition, Map)  
     * @see com.alibaba.nacos.spring.util.config.NacosConfigLoader#load(String, String, Properties)
     * @see com.alibaba.nacos.client.config.NacosConfigService#getConfig(String, String, long)
     *
     * update 定时任务线程池
     * @see ClientWorker.LongPollingRunnable#run()
     * @see CacheData#checkListenerMd5()
     * @see CacheData#safeNotifyListener(String, String, String, String, String, ManagerListenerWrap)
     * @see com.alibaba.nacos.spring.context.event.config.DelegatingEventPublishingListener#receiveConfigInfo(String)
     * @see com.alibaba.nacos.spring.context.event.config.DelegatingEventPublishingListener#onReceived(String)
     * @see com.alibaba.nacos.spring.core.env.NacosPropertySourcePostProcessor#addListenerIfAutoRefreshed->listener ()->{} //回掉更新NacosPropertySource  copy old->new 替换spring env old
     * @see com.alibaba.nacos.spring.context.event.config.DelegatingEventPublishingListener#publishEvent(String)
     * @see org.springframework.context.support.AbstractApplicationContext#publishEvent(ApplicationEvent)
     * @see org.springframework.context.event.SimpleApplicationEventMulticaster#multicastEvent(ApplicationEvent)
     * @see com.alibaba.nacos.spring.context.annotation.config.NacosValueAnnotationBeanPostProcessor#onApplicationEvent(NacosConfigReceivedEvent) //重新set值 此处暂时有bug
     * @see ClientWorker#checkConfigInfo() //长轮询配置中心信息
     */
    @NacosValue("${simple.name}")
    String userName;

    @Value("${app.name}")
    String appName;

//    @NacosValue("${user.age}")
//    int age;

    @GetMapping("/getName")
    @ResponseBody
    public ModelResult<String> getName() {
        ModelResult<String> result=new ModelResult<>();
        result.setData(userName+"->");
        return result;
    }
    @GetMapping("/getAppName")
    @ResponseBody
    public ModelResult<String> getAppName(){
        ModelResult<String> result=new ModelResult<>();
        result.setData(appName+"->");
        return result;
    }

}

package com.github.simple.demo.start;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleComponentScan;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.context.SimpleApplicationContext;
import com.github.simple.demo.service.*;
import com.github.simple.demo.service.aop.LogService;
import com.github.simple.demo.test.MockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:47 下午
 * @description: StartIocApplication
 * @see com.github.simple.core.beans.factory.AbsBeanFactory#doGetBean(String)
 */
@SimpleComponentScan(basePackages = "com.github.simple.demo")
public class StartIocApplication {
    private static final Logger logger = LoggerFactory.getLogger(StartIocApplication.class);

    public static void main(String[] args) throws Throwable {
        SimpleApplicationContext applicationContext = SimpleApplicationContext.run(StartIocApplication.class);
        //自定义事件广播机制
        applicationContext.publishEvent(new MockEvent("MockEvent test","MockEvent"));
//        applicationContext.publishEvent(new HelloEvent("@@@@hello event test","我是hello我在测试"));
//        A a = beanFactory.getBean(A.class);
        /**
         * bean 工厂 注册bean
         * @see com.github.simple.demo.service.CustomBeanFactoryProcessor#postProcessBeanFactory(SimpleBeanFactory)
         * {@code BeanFactoryRegistryBeanTest brb=beanFactory.getBean(BeanFactoryRegistryBeanTest.class);}
         */
//        BeanFactoryRegistryBeanTest brb=beanFactory.getBean(BeanFactoryRegistryBeanTest.class);

        /**
         * 获取代理bean
         */
//        B b = beanFactory.getBean(B.class);
        ConfigBeanTest configBeanTest = applicationContext.getBean(ConfigBeanTest.class);

        logger.info(" configBeanTest output:{}", JSONObject.toJSONString(configBeanTest));
        logger.info("=================================**********=======================================");
        ConfigBeanTest2 configBeanTest2 = applicationContext.getBean(ConfigBeanTest2.class);

        logger.info(" configBeanTest2 output:{}", JSONObject.toJSONString(configBeanTest2));

        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);

        logger.info(" redisTemplate output:{}", redisTemplate.getMax());

//         logger.info(a.tasks());



        C c = applicationContext.getBean(C.class);
        c.sendCMsg();

        D d = applicationContext.getBean(D.class);
        d.dTest();

        RegistryBean registryBean=applicationContext.getBean(RegistryBean.class);

        registryBean.test();

        LogService logService=applicationContext.getBean(LogService.class);
        logger.info("==> aop logService output:{}",logService.testLog());

    }
}

package com.github.simple.demo.start;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleComponentScan;
import com.github.simple.core.beans.factory.SimpleBeanFactory;
import com.github.simple.core.context.SimpleApplicationContext;
import com.github.simple.demo.service.*;
import com.github.simple.demo.service.aop.LogService;
import com.github.simple.demo.service.circular.C;
import com.github.simple.demo.service.circular.D;
import com.github.simple.demo.service.config.domain.MethodBean1;
import com.github.simple.demo.service.config.domain.MethodBean2;
import com.github.simple.demo.test.MockEvent;
import com.github.simple.spi.EsHolder;
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

    /**
     * 启动类
     *
     * @param args arg
     * @return void
     * @author jianlei.shi
     * @date 2021-01-25 17:41:22
     */
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
        MethodBean1 configBeanTest = applicationContext.getBean(MethodBean1.class);

        logger.info(" configBeanTest output:{}", JSONObject.toJSONString(configBeanTest));
        logger.info("=================================**********=======================================");
        MethodBean2 configBeanTest2 = applicationContext.getBean(MethodBean2.class);

        logger.info(" configBeanTest2 output:{}", JSONObject.toJSONString(configBeanTest2));

        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);

        logger.info(" redisTemplate output:{}", redisTemplate.toString());

//         logger.info(a.tasks());



        C c = applicationContext.getBean(C.class);
        c.sendCMsg();

        D d = applicationContext.getBean(D.class);
        d.dTest();

        RegistryBean registryBean=applicationContext.getBean(RegistryBean.class);

        registryBean.test();

        LogService logService=applicationContext.getBean(LogService.class);
        logger.info("==> aop logService output:{}",logService.testLog());
        final EsHolder esHolder = applicationContext.getBean(EsHolder.class);
        logger.info("==>es holder toString :{}",esHolder);

    }
}

package com.github.simple.demo.start;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleComponentScan;
import com.github.simple.core.factory.SimpleBeanFactory;
import com.github.simple.core.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.demo.service.ConfigBeanTest;
import com.github.simple.demo.service.ConfigBeanTest2;
import com.github.simple.demo.service.Order;
import com.github.simple.demo.service.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:47 下午
 * @description: StartIocApplication
 */
@SimpleComponentScan(basePackages = "com.github.simple.demo")
public class StartIocApplication {
    private static final Logger logger = LoggerFactory.getLogger(StartIocApplication.class);

    public static void main(String[] args) throws Throwable {
        SimpleDefaultListableBeanFactory beanFactory = SimpleDefaultListableBeanFactory.run(StartIocApplication.class);
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
        ConfigBeanTest configBeanTest = beanFactory.getBean(ConfigBeanTest.class);

        logger.info(" configBeanTest output:{}", JSONObject.toJSONString(configBeanTest));
        logger.info("=================================**********=======================================");
        ConfigBeanTest2 configBeanTest2 = beanFactory.getBean(ConfigBeanTest2.class);

        logger.info(" configBeanTest2 output:{}", JSONObject.toJSONString(configBeanTest2));

        RedisTemplate redisTemplate = beanFactory.getBean(RedisTemplate.class);

        logger.info(" redisTemplate output:{}", JSONObject.toJSONString(redisTemplate));

//         logger.info(a.tasks());
        logger.info("->beans size:{}", beanFactory.getBeans().size());

        Map<String, Order> beans = beanFactory.getBeansOfType(Order.class);
        logger.info("==>{}", JSONObject.toJSONString(beans));

    }
}
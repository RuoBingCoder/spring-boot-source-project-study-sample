package com.github.simple.demo.start;

import com.alibaba.fastjson.JSONObject;
import com.github.simple.core.annotation.SimpleComponentScan;
import com.github.simple.core.factory.SimpleBeanFactory;
import com.github.simple.core.factory.SimpleDefaultListableBeanFactory;
import com.github.simple.demo.service.B;
import com.github.simple.demo.service.Order;

import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/12/12 3:47 下午
 * @description: StartIocApplication
 */
@SimpleComponentScan(basePackages = "com.github.simple.demo")
public class StartIocApplication {

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
        B b = beanFactory.getBean(B.class);

        System.out.println("b output:"+b.hello());
//        System.out.println(a.tasks());
        System.out.println("->beans size:"+beanFactory.getBeans().size());

        Map<String, Order> beans = beanFactory.getBeansOfType(Order.class);
        System.out.println("==>"+ JSONObject.toJSONString(beans));

    }
}

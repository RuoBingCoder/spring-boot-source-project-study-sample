package com.sjl.spring.components;

import com.sjl.spring.components.event.CustomEvent;
import com.sjl.spring.components.utils.SpringUtil;
import org.apache.tomcat.util.net.NioEndpoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@MapperScan("com.sjl.spring.components.transaction.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@Order
@EnableRetry
public class SpringComponentsApplication implements CommandLineRunner {



    public static void main(String[] args) {
        //如果是容器启动之前注册事件则需要进行以下操作
      /*  =============================容器创建之前=====================================
        SpringApplication springApplication = new SpringApplication();
        springApplication.addListeners(new CustomApplicationListener());
        springApplication.run(SpringComponentsApplication.class,args);
        ================================结束==================================*/

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringComponentsApplication.class, args);
        applicationContext.publishEvent(new CustomEvent("这是自定义事件"));
//        applicationContext.close();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====================CommandLineRunner========================");
//        ReflectUtil.threadClassLoader("");

        Object tomcat = SpringUtil.tomcatBean.get(NioEndpoint.class);

        System.out.println("===>>"+tomcat.getClass());

        SpringUtil.dependencyLookup();

    }
}

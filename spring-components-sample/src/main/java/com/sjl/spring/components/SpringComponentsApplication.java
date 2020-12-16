package com.sjl.spring.components;

import com.sjl.spring.components.event.CustomEvent;
import com.sjl.spring.components.pojo.GeoHolder;
import com.sjl.spring.components.service.LazyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.EnableRetry;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.sjl.spring.components.transaction.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Order
@EnableRetry
public class SpringComponentsApplication implements CommandLineRunner {
    /**
     * 推荐ObjectProvider 延迟注入 避免风险
     * {@link ObjectFactory#getObject()}
     */
    @Resource
    private ObjectProvider<GeoHolder> opGeoHolder;

    /**
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.ResourceElement#getResourceToInject(Object, String)
     * 创建代理
     */
    @Resource
    @Lazy
    private LazyService lazyService;


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
        lazyService.testLazy();
//        ReflectUtil.threadClassLoader("");
        System.out.println("->>>>>>"+opGeoHolder.getIfAvailable());

    }
}

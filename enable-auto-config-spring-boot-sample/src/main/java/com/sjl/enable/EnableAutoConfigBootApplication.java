package com.sjl.enable;

import com.alibaba.fastjson.JSONObject;
import com.sjl.enable.annotation.SjlScanner;
import com.sjl.enable.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@SpringBootApplication
@SjlScanner(basePackages = "com.sjl.enable.service")
@EnableScheduling
@Slf4j
public class EnableAutoConfigBootApplication implements CommandLineRunner, ApplicationRunner, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private HelloService helloService;


    public static void main(String[] args) {
        SpringApplication.run(EnableAutoConfigBootApplication.class, args);


    }

    /**
     * @param args
     * @description 属于Spring boot回掉最后回掉
     * @see org.springframework.boot.SpringApplication#callRunners(ApplicationContext, ApplicationArguments)
     */
    @Override
    public void run(String... args) {
        log.info("===> hello output:{}", helloService.say());
    }

    /**
     * @return null
     * @author jianlei.shi
     * @description 属于Spring回掉
     * @date 2:09 下午 2020/12/25
     * @see org.springframework.context.support.AbstractApplicationContext#publishEvent(Object)
     **/
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()==null) {
            HelloService bean = event.getApplicationContext().getBean(HelloService.class);
            log.info("===>CommandLineRunner getBean :{}", bean.say());
        }

    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description
     * @date 2:12 下午 2020/12/25
     * @see org.springframework.boot.SpringApplication#callRunners(ApplicationContext, ApplicationArguments)
     **/
    @Override
    public void run(ApplicationArguments args) {
        log.info("===> ApplicationRunner output :{}", JSONObject.toJSONString(args.getOptionNames()));

    }
}

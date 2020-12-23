package com.sjl.bean.life.cycle;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import static org.springframework.core.io.support.SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION;

@SpringBootApplication
@ComponentScan(value = {"cn.hutool.extra.spring", "com.sjl.bean.life.cycle"})
@Import(SpringUtil.class)
public class BeanLifeCycleApplication implements CommandLineRunner {

    @Autowired
    private String hello;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(BeanLifeCycleApplication.class, args);
//    run.getBean(ServiceImpl.class).doSave();
//    run.close();

    }

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classLoader = BeanLifeCycleApplication.class.getClassLoader();
        Enumeration<URL> urls = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            UrlResource resource = new UrlResource(url);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                System.out.println("====>>>"+ JSONObject.toJSONString(entry.getKey()));
            }
        }
        System.out.println("==>hello call back :"+hello);


    }
}

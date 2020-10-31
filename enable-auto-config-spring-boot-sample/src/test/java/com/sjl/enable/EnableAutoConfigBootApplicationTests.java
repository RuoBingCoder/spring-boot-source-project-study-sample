package com.sjl.enable;

//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

@SpringBootTest
class EnableAutoConfigBootApplicationTests {

    @Test
    void contextLoads() {
        ClassUtils.isPresent(null,this.getClass().getClassLoader()); //判断某个bean是否在容器中扫描路径下
        ConfigurableApplicationContext context = null;
        context.containsBeanDefinition(""); //判断容器是否包含某个bean

    }

}

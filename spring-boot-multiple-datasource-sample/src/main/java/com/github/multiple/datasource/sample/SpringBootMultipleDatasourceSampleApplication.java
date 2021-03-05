package com.github.multiple.datasource.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多个数据源示例应用程序
 *
 * @author shijianlei
 * @date 2021-03-01 16:27:08
 */
@SpringBootApplication
//@EnableConfigurationProperties
public class SpringBootMultipleDatasourceSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMultipleDatasourceSampleApplication.class, args);
    }

}

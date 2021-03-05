package com.github.sharding.sample;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分片示例应用程序
 * @see org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration 配置ShardingDataSource
 * @see  org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection
 * @see org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource
 * @author shijianlei
 * @date 2021-03-01 19:05:45
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ShardingSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSampleApplication.class, args);
    }

}

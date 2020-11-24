package com.sjl.es.spring.boot.sample.config;

import cn.hutool.core.lang.Assert;
import com.sjl.es.spring.boot.sample.utils.StringUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * @version : V1.0
 * @ClassName: ElasticSearchClientConfig
 * @Description: TODO
 * @Auther: wangqiang
 * @Date: 2020/4/22 17:13
 */
@Configuration
public class ElasticSearchClientConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String host;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        String[] parseHost = StringUtil.parseHost(host);
        return new RestHighLevelClient(RestClient.builder(new HttpHost(parseHost[1].substring(2), Integer.parseInt(parseHost[2]), parseHost[0])));
    }

}

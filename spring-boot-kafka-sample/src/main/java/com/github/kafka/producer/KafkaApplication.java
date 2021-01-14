package com.github.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import com.github.kafka.message.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: JianLei
 * @date: 2020/11/19 下午7:46
 * @description: KafkaApplication
 */
@Component
public class KafkaApplication implements CommandLineRunner {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${kafka.producer.topic}")
    private String producerTopic;
    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        while (true) {
            writer2Kafka(random);
            // 每隔3s向kafka发送数据
            TimeUnit.SECONDS.sleep(3);
        }


    }
    private void writer2Kafka(Random random) {
        CustomMessage metric = new CustomMessage();
        metric.setHostname("127.0.0.1");
        metric.setTotal(1L);
        metric.setMsg("这是生产者发送的消息");
        kafkaTemplate.send(producerTopic, JSONObject.toJSONString(metric));
        kafkaTemplate.flush();
    }
}

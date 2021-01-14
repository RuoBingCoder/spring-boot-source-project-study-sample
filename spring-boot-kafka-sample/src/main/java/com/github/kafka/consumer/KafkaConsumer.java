package com.github.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.kafka.message.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author: JianLei
 * @date: 2020/11/19 下午7:49
 * @description: KafkaConsumer
 * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor#findListenerAnnotations(Method)
 */
@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "test-producer")
    public void event(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        kafkaMessage.ifPresent(x -> {
            CustomMessage msg = JSONObject.parseObject(x, CustomMessage.class);
            log.info("=======>>>消费kafka中的数据:{}", msg);
        });
    }
}

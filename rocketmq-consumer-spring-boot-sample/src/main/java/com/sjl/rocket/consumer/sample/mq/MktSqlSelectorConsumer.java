package com.sjl.rocket.consumer.sample.mq;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import pojo.Greeting;

/**
 * @author: JianLei
 * @date: 2020/9/18 11:10 上午
 * @description: 有选择的消费消息
 */

@Log4j2
@Service
@RocketMQMessageListener(
        topic = "greetings-topic",
        selectorExpression = " letter = 'm' or letter = 'k' or letter = 't' ",
        selectorType = SelectorType.TAG,
        consumerGroup = "sql-consumer-group-mkt"
)
public class MktSqlSelectorConsumer implements RocketMQListener<Greeting> {

    @Override
    public void onMessage(Greeting greeting) {
        log.info("'m', 'k', 't': " + greeting.toString());
    }
}




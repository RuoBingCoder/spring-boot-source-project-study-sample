package com.sjl.rocket.consumer.sample.mq;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/9/18 10:44 上午
 * @description: 后使用RocketMQTemplate将该有效内容发送到Apache
 *     RocketMQ主题greetings-topic。在这里，我们使用了RocketMQTemplate接受的对象的重载MessagePostProcessor
 *     。的MessagePostProcessor是，我们可以将Spring框架的回调Message会被发送出去的对象。在此示例中，我们贡献一个标头值letter，其中包含名称的第一个字母。我们将在消费者中使用它。
 *     <p>让我们看一下消费者。从Spring Initializr生成一个新的Spring Boot应用程序，并确保添加Apache
 *     RocketMQ自动配置。您还需要application.properties为客户端指定名称服务器。
 *     <p>自动配置支持定义实现的bean RocketMQListener<T>，其中T消费者将接收的有效负载类型。在这种情况下，有效负载是Greeting。
 */
/*@Log4j2
@Service
@RocketMQMessageListener(topic = "greetings-topic", consumerGroup = "simple-group")
class SimpleConsumer implements RocketMQListener<Greeting> {

  @Override
  public void onMessage(Greeting greeting) {
    log.info("=>消费者收到消息:{}", greeting.toString());
  }
}
*/
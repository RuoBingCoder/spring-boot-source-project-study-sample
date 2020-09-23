package com.sjl.rocketmq.producers.sample;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;
import pojo.Greeting;

import java.time.Instant;
@RequiredArgsConstructor
@SpringBootApplication
public class RocketmqProducersSpringBootSampleApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(RocketmqProducersSpringBootSampleApplication.class, args);
    String property = context.getEnvironment().getProperty("rocketmq.name-server");
    System.out.println("===>>"+property);
  }
  /**
   * 该事件表示application应该初始化完成，可以准备接收请求。
   *
   * @param template
   * @return
   */
  @Bean
  ApplicationListener<ApplicationReadyEvent> ready(RocketMQTemplate template) {
    return event -> {

      var now = Instant.now();
      var destination = "greetings-topic";

      for (var name : "Tammie,Kimly,Josh,Rob,Mario,Mia".split(",")) {

        var payload = new Greeting("Hello @ " + name + " @ " + now.toString());
        var messagePostProcessor = new MessagePostProcessor() {

          @Override
          public Message<?> postProcessMessage(Message<?> message) {
            var headerValue = Character.toString(name.toLowerCase().charAt(0));
            return MessageBuilder
                    .fromMessage(message)
                    .setHeader("letter", headerValue)
                    .build();
          }
        };
        template.convertAndSend(destination, payload, messagePostProcessor);
      }
    };
  }
}

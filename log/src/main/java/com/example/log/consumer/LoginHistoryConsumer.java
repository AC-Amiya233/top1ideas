package com.example.log.consumer;

import com.example.log.config.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = {MQConfig.TOPIC_LOGIN_HISTORY_NAME})
public class LoginHistoryConsumer {
    @RabbitHandler
    public void receiveViewHistory(String msg) {
        log.info(msg);
    }
}
package com.example.user.consumer;

import com.example.user.config.MQConfig;
import com.example.user.service.HomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = {MQConfig.TOPIC_REGISTER_HISTORY_NAME})
public class RegisterConsumer {
    @Autowired
    private HomePageService homePageService;

    @RabbitHandler
    public void registerHandler(String msg) {
        // register,[uid]
        log.info(msg);
        String[] items = msg.split(",");
        Long id = Long.parseLong(items[1]);
        homePageService.insertHomePage(id);
    }
}

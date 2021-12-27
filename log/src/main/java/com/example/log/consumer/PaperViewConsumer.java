package com.example.log.consumer;

import com.example.log.config.MQConfig;
import com.example.log.entity.View;
import com.example.log.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = {MQConfig.TOPIC_PAPER_VIEW_NAME})
public class PaperViewConsumer {
    @Autowired
    private ViewService viewService;
    @RabbitHandler
    public void receiveViewHistorr(String msg) {
        String[] items = msg.split(",");
        String action = items[0];
        Long uid = Long.parseLong(items[1]);
        Long pid = Long.parseLong(items[2]);
        log.info("Action {}, uid {}, pid {}", action, uid, pid);
        viewService.insertView(View.builder().uid(uid).pid(pid).build());
    }
}
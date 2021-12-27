package com.example.log.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String TOPIC_LOGIN_HISTORY_NAME = "login-history";
    public static final String TOPIC_REGISTER_HISTORY_NAME = "register-history";
    public static final String TOPIC_PAPER_VIEW_NAME = "paper-view";

    public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String DEFAULT_LOGIN_ROUTING_FORMAT_KEY = "topic.{}.login";
    public static final String DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY = "topic.{}.view";

    public static final String TOPIC_LOGIN_HISTORY_ROUTING_KEY = "topic.#.login";
    public static final String TOPIC_REGISTER_ROUTING_KEY = "topic.#.register";
    public static final String TOPIC_PAPER_VIEW_ROUTING_KEY = "topic.#.view";

    @Bean
    public Queue loginHistoryQueue() {
        return new Queue(TOPIC_LOGIN_HISTORY_NAME, true);
    }

    @Bean
    public Queue paperViewQueue() {
        return new Queue(TOPIC_PAPER_VIEW_NAME, true);
    }

    @Bean
    public Queue registerQueue() {
        return new Queue(TOPIC_REGISTER_HISTORY_NAME, true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingLogin() {
        return BindingBuilder.bind(loginHistoryQueue()).to(topicExchange()).with(TOPIC_LOGIN_HISTORY_ROUTING_KEY);
    }

    @Bean
    public Binding bindingPaperView() {
        return BindingBuilder.bind(paperViewQueue()).to(topicExchange()).with(TOPIC_PAPER_VIEW_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRegister() {
        return BindingBuilder.bind(registerQueue()).to(topicExchange()).with(TOPIC_REGISTER_ROUTING_KEY);
    }
}

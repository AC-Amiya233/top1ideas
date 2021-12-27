package com.example.papers.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String TOPIC_LOGIN_HISTORY_NAME = "login-history";
    public static final String TOPIC_PAPER_VIEW_NAME = "paper-view";

    public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String DEFAULT_LOGIN_ROUTING_FORMAT_KEY = "topic.{}.login";
    public static final String DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY = "topic.{}.view";

    public static final String TOPIC_LOGIN_HISTORY_ROUTING_KEY = "topic.#.login";
    public static final String TOPIC_PAPER_VIEW_ROUTING_KEY = "topic.#.view";
}

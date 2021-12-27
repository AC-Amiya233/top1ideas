package com.example.login.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String TOPIC_LOGIN_HISTORY_NAME = "login-history";
    public static final String TOPIC_REGISTER_HISTORY_NAME = "register-history";
    public static final String TOPIC_PAPER_VIEW_NAME = "paper-view";

    public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String DEFAULT_LOGIN_ROUTING_FORMAT_KEY = "topic.%d.login";
    public static final String DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY = "topic.%d.view";
    public static final String DEFAULT_REGISTER_ROUTING_FORMAT_KEY = "topic.%d.register";

    public static final String TOPIC_LOGIN_HISTORY_ROUTING_KEY = "topic.#.login";
    public static final String TOPIC_REGISTER_ROUTING_KEY = "topic.#.register";
    public static final String TOPIC_PAPER_VIEW_ROUTING_KEY = "topic.#.view";
}

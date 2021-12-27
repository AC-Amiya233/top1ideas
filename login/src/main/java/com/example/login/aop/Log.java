package com.example.login.aop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A POJO class
 * @author DING Yaohonog
 * @date 2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String threadId;
    private String threadName;
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Object result;
    private Long timeCost;
    private String os;
    private String browser;
    private String userAgent;
}

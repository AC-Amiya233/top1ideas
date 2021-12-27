package com.example.login.controller;

import cn.hutool.http.cookie.ThreadLocalCookieStore;
import com.example.login.util.BloomingFilterUtil;
import com.example.login.entity.Account;
import com.example.login.predefined.Result;
import com.example.login.service.UserService;
import com.taptap.ratelimiter.annotation.RateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.example.login.util.CookieUtil;

import static com.example.login.config.CacheConfig.DEFAULT_SESSION_TIMEOUT_EXPIRE_TIME;
import static com.example.login.config.CacheConfig.DEFAULT_SESSION_TIMEOUT_EXPIRE_TIME_TYPE;
import static com.example.login.config.MQConfig.*;
import static com.research.teamwork.interceptor.AopLog.getIp;

@RestController
@Slf4j
@RequestMapping("/sso")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/login")
    @ResponseBody
    @RateLimit(rate = 10, rateInterval = "3s")
    public Object login(@RequestParam(value = "account", required = true) String account,
                        @RequestParam(value = "password", required = true) String password,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Result result = Result.Processing;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        //query read database
        String ip = getIp(request);
        log.info("IP: {} request login with parameter account: {}, password:{}",
                ip,
                account,
                password);
        String redisIp = String.format("ip:%s", ip);
        if (!BloomingFilterUtil.contains(account) || !BloomingFilterUtil.contains(password)) {
            Object ipCnt = operations.get(redisIp);
            if (ipCnt == null) {
                operations.set(redisIp, 1, 60, TimeUnit.SECONDS);
                result = Result.InvalidInput;
            }
            else {
                Integer cnt = (Integer) ipCnt;
                if (cnt > 2) {
                    redisTemplate.expire(redisIp, 120, TimeUnit.SECONDS);
                    result = Result.AttackSuspicion;
                }
                else {
                    operations.increment(redisIp);
                    result = Result.InvalidInput;
                }
            }

            log.info("Request declined");
        }
        else {
            Account info = userService.findByAccount(account);
            if (info != null) {
                if (password.equals(info.getPwd())) {
                    result = Result.LoginSucceed;

                    //allocate session
                    log.info("Allocation Session: ", session.getId());
                    CookieUtil.writeLoginToken(response, session.getId());
                    operations.set(String.format("%s:%s", "token", session.getId()), info.getUid(),
                            DEFAULT_SESSION_TIMEOUT_EXPIRE_TIME,
                            DEFAULT_SESSION_TIMEOUT_EXPIRE_TIME_TYPE);

                    log.info("Request accept");
                }
                else {
                    Object ipCnt = operations.get(redisIp);
                    if (ipCnt == null) {
                        operations.set(redisIp, 1, 60, TimeUnit.SECONDS);
                        result = Result.WrongPassword;
                    }
                    else {
                        Integer cnt = (Integer) ipCnt;
                        if (cnt > 2) {
                            redisTemplate.expire(redisIp, 120, TimeUnit.SECONDS);
                            result = Result.AttackSuspicion;
                        }
                        else {
                            operations.increment(redisIp);
                            result = Result.WrongPassword;
                        }
                    }

                    log.info("Request declined");
                }
            }
            else {
                Object ipCnt = operations.get(redisIp);
                if (ipCnt == null) {
                    operations.set(redisIp, 1, 60, TimeUnit.SECONDS);
                    result = Result.NoMatchAccount;
                }
                else {
                    Integer cnt = (Integer) ipCnt;
                    if (cnt > 2) {
                        redisTemplate.expire(redisIp, 120, TimeUnit.SECONDS);
                        result = Result.AttackSuspicion;
                    }
                    else {
                        operations.increment(redisIp);
                        result = Result.NoMatchAccount;
                    }
                }

                log.info("Request declined");
            }
        }
        return result;
    }

    @PostMapping("/register")
    @ResponseBody
    @Transactional
    @RateLimit(rate = 10, rateInterval = "3s")
    public Object register(@RequestParam(value = "account", required = true) String account,
                           @RequestParam(value = "password", required = true) String password,
                           HttpServletRequest request) {
        Result result = Result.Processing;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        String ip = getIp(request);
        log.info("IP: {} request login with parameter account: {}, password:{}",
                ip,
                account,
                password);
        String redisIp = String.format("ip:%s", ip);
        if (!BloomingFilterUtil.contains(account) || !BloomingFilterUtil.contains(password)) {
            Object ipCnt = operations.get(redisIp);
            if (ipCnt == null) {
                operations.set(redisIp, 1, 60, TimeUnit.SECONDS);
                result = Result.InvalidInput;
            }
            else {
                Integer cnt = (Integer) ipCnt;
                if (cnt > 2) {
                    redisTemplate.expire(redisIp, 120, TimeUnit.SECONDS);
                    result = Result.AttackSuspicion;
                }
                else {
                    operations.increment(redisIp);
                    result = Result.InvalidInput;
                }
            }

            log.info("Request declined");
        }
        else {
            Date curr = new Date();
            userService.registerUser(Account.builder()
                    .account(account)
                    .pwd(password)
                    .creationDate(curr)
                    .latestLoginDate(curr).build());
            Long uid = userService.findByAccount(account).getUid();
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,
                    String.format(DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY, uid),
                    String.format("Register,%d", uid));

            log.info("Request accept");
            return Result.QuerySucceed;
        }
        return result;
    }

    @GetMapping("/logout")
    @ResponseBody
    @RateLimit(rate = 10, rateInterval = "3s")
    public Object logout(@RequestParam(value = "token", required = true) String token) {
        Result result = Result.Processing;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        String tokenKey = String.format("%s:%s", "token", token);
        Object redisToken = operations.get(tokenKey);
        if (redisToken == null) {
            log.info("Token already expired");
            //do nothing
        }
        else {
            log.info("Token {} is not expired", tokenKey);
            redisTemplate.delete(tokenKey);
        }
        result = Result.LogoutSucceed;
        return result;
    }
}

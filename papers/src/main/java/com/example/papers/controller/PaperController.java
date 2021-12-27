package com.example.papers.controller;

import cn.hutool.db.Session;
import com.example.papers.entity.Paper;
import com.example.papers.predefined.Result;
import com.example.papers.service.PaperService;
import com.example.papers.util.BloomingFilterUtil;
import com.example.papers.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.papers.config.MQConfig.*;

@Slf4j
@RestController
public class PaperController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PaperService paperService;

    @GetMapping("/paper")
    @ResponseBody
    public Object homepage(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = true) Integer pageSize,
                       @RequestParam(value = "token", required = true) String token,
                       HttpServletResponse response) {
        Result result = Result.Processing;
        List<Paper> queried = null;

        String redisSession = String.format("%s:%s", "token", token);
        log.info("Redis query: {}",redisSession);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object redisToken = operations.get(redisSession);
        if (token == null || redisToken == null) {
            result = Result.LoginRequired;
            if (token == null) {
                log.info("User Request Without Any Explict Token");
            }
            else if (redisToken == null) {
                log.info("User Token Expired");
            }

            try {
                response.sendRedirect("/login_required");
            }
            catch(Exception e) {
                log.error("Redirect failure");
            }
        }
        else {
            queried = paperService.findAll(pageNumber, pageSize);
            result = Result.QuerySucceed;
            for (Paper paper: queried) {
                // log.info("id:{},queried:{}",redisToken, paper.getPid());

                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,
                        String.format(DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY, redisToken),
                        String.format("Query,%d,%d",redisToken, paper.getPid()));
            }
        }

        return String.format("%s\n%s", result, queried);
    }

    @GetMapping("/search")
    @ResponseBody
    public Object search(@RequestParam(value = "keyword", required = true) String keyword,
                         @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
                         @RequestParam(value = "pageSize", required = true) Integer pageSize,
                         @RequestParam(value = "token", required = true) String token,
                         HttpServletResponse response) {
        if (!BloomingFilterUtil.contains(keyword)) {
            return Result.InvalidInput;
        }
        Result result = Result.Processing;
        List<Paper> queried = null;

        String redisSession = String.format("%s:%s", "token", token);
        log.info("Redis query: {}",redisSession);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object redisToken = operations.get(redisSession);
        if (token == null || redisToken == null) {
            result = Result.LoginRequired;
            if (token == null) {
                log.info("User Request Without Any Explict Token");
            }
            else if (redisToken == null) {
                log.info("User Token Expired");
            }

            try {
                response.sendRedirect("/login_required");
            }
            catch(Exception e) {
                log.error("Redirect failure");
            }
        }
        else {
            queried = paperService.findByKeyword(keyword, pageNumber, pageSize);
            result = Result.QuerySucceed;

            for (Paper paper: queried) {
                // log.info("id:{},queried:{}",redisToken, paper.getPid());
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE,
                        String.format(DEFAULT_PAPER_VIEW_ROUTING_FORMAT_KEY, redisToken),
                        String.format("Query,%d,%d",redisToken, paper.getPid()));
            }
        }

        return String.format("%s\n%s", result, queried);
    }

    @GetMapping("/login_required")
    public String redirct(HttpServletRequest request, HttpServletResponse response) {
        return "Go to localhost:8080 verify yourself";
    }
}
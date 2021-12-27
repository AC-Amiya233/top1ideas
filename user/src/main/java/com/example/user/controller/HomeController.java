package com.example.user.controller;

import com.taptap.ratelimiter.annotation.RateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    @RateLimit(rate = 10, rateInterval = "1s")
    public Object UserHome() {
        return "Welcome to User Center";
    }
}

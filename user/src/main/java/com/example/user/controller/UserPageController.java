package com.example.user.controller;

import com.example.user.entity.Homepage;
import com.example.user.predefined.Result;
import com.example.user.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserPageController {
    @Autowired
    private HomePageService homePageService;

    @GetMapping("/homepage")
    public Object homepage(@RequestParam(value = "uid", required = true) Long uid) {
        if (uid / 100000 == 0) {
            return Result.NoMatchAccount;
        }
        else {
            Result result = Result.Processing;
            Homepage queried = homePageService.findHomepageById(uid);
            if (queried == null) {
                result = Result.NoMatchAccount;
            }
            else {
                result = Result.QuerySucceed;
            }
            return String.format("%s\n%s", result, queried);
        }
    }
}

package com.example.login;

import com.example.login.config.BloomingFilterConfig;
import com.example.login.util.BloomingFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication implements ApplicationRunner, CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BloomingFilterUtil.init(BloomingFilterConfig.keywordChars);
        System.out.println("Blooming Filter Warm Up");
    }

    @Override
    public void run(String... args) throws Exception {
        // bloomFilter.init(BloomingFilterConsts.keywordChars);
        // System.out.println("Blooming Filter Warm Up");
    }
}
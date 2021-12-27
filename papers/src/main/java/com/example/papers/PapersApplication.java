package com.example.papers;

import com.example.papers.config.BloomingFilterConfig;
import com.example.papers.util.BloomingFilterUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PapersApplication implements ApplicationRunner, CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PapersApplication.class, args);
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

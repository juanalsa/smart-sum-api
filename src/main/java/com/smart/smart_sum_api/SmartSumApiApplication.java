package com.smart.smart_sum_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class SmartSumApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSumApiApplication.class, args);
    }

}

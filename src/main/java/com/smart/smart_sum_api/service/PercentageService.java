package com.smart.smart_sum_api.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PercentageService {

    private Random random = new Random();

    public PercentageService(Random random) {
        this.random = random;
    }

    public PercentageService() {
        this(new Random());
    }

    @Cacheable("dynamic-percentage")
    public BigDecimal getCurrentPercentage() {
        try {
            Thread.sleep(5000); // Simulate a delay 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (random.nextBoolean()) {
            return new BigDecimal("0.10");
        } else {
            throw new RuntimeException("Percentage service failed");
        }
    }
}

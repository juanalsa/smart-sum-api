package com.smart.smart_sum_api.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PercentageService {

    @Cacheable("dynamic-percentage")
    public BigDecimal getCurrentPercentage() {
        try {
            Thread.sleep(5000); // Simulate a delay 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new BigDecimal("0.10");
    }
}

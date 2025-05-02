package com.smart.smart_sum_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PercentageServiceTest {

    private PercentageService percentageService;
    private Random mockRandom;

    @BeforeEach
    void setup() {
        mockRandom = Mockito.mock(Random.class);
        percentageService = new PercentageService(mockRandom);
    }

    @Test
    void should_return_fixed_percentage() {
        // Arrange
        when(mockRandom.nextBoolean()).thenReturn(true);

        // Act
        BigDecimal percentage = percentageService.getCurrentPercentage();

        // Assert
        assertEquals(new BigDecimal("0.10"), percentage);
    }
}
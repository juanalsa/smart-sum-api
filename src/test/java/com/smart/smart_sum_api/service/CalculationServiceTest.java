package com.smart.smart_sum_api.service;

import com.smart.smart_sum_api.dto.CalculationRequest;
import com.smart.smart_sum_api.dto.CalculationResponse;
import com.smart.smart_sum_api.exception.ApiException;
import com.smart.smart_sum_api.exception.PercentageServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @Mock
    private PercentageService percentageService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CalculationService calculationService;

    @Test
    void should_return_correct_calculation() {
        // Arrange
        CalculationRequest request = new CalculationRequest();
        request.setNum1(new BigDecimal("10"));
        request.setNum2(new BigDecimal("20"));
        BigDecimal expectedResult = new BigDecimal("33.0");

        when(percentageService.getCurrentPercentage()).thenReturn(new BigDecimal("0.10"));

        // Act
        CalculationResponse response = calculationService.calculate(request);

        // Assert
        assertEquals(0, response.getResult().compareTo(expectedResult));
    }

    @Test
    void should_throw_exception_when_percentage_fails() {
        // Arrange
        CalculationRequest request = new CalculationRequest();
        request.setNum1(new BigDecimal("10"));
        request.setNum2(new BigDecimal("20"));

        when(percentageService.getCurrentPercentage())
                .thenThrow(new PercentageServiceUnavailableException("Error external service failed"));

        // Act & Assert
        ApiException ex = assertThrows(ApiException.class, () ->
                calculationService.calculate(request));

        assertEquals("Error no cached percentage available and external service failed", ex.getMessage());
    }
}
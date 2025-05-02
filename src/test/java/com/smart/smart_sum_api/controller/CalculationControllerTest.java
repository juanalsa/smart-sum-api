package com.smart.smart_sum_api.controller;

import com.smart.smart_sum_api.dto.CalculationResponse;
import com.smart.smart_sum_api.exception.PercentageServiceUnavailableException;
import com.smart.smart_sum_api.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculationController.class)
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private CalculationService calculationService;

    @BeforeEach
    void setupCache() {
        cacheManager.getCache("dynamic-percentage").clear();
        cacheManager.getCache("dynamic-percentage").put("dynamic-percentage", new BigDecimal("0.10"));
    }

    @Test
    void should_return_calculated_result() throws Exception {
        // Arrange
        String body = """
                    {
                        "num1": 10,
                        "num2": 20
                    }
                """;
        BigDecimal expectedResult = new BigDecimal("33.0");

        when(calculationService.calculate(any())).thenReturn(new CalculationResponse(expectedResult));

        // Act & Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

    @Test
    void should_return_validation_error_when_num1_is_null() throws Exception {
        // Arrange
        String body = """
                    {
                        "num2": 20
                    }
                """;

        BigDecimal expectedResult = new BigDecimal("33.0");

        when(calculationService.calculate(any())).thenReturn(new CalculationResponse(expectedResult));

        // Act & Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("[num1 cannot be null]"));
    }

    @Test
    void should_return_service_unavailable_when_percentage_missing() throws Exception {
        // Arrange
        cacheManager.getCache("dynamic-percentage").clear(); // Simula fallo sin caché

        String body = """
                    {
                        "num1": 1,
                        "num2": 1
                    }
                """;

        when(calculationService.calculate(any()))
                .thenThrow(new PercentageServiceUnavailableException("Error external service failed"));

        // Act & Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Error external service failed"));
    }
}
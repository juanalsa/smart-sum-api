package com.smart.smart_sum_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculationRequest {

    @NotNull(message = "num1 cannot be null")
    private BigDecimal num1;

    @NotNull(message = "num2 cannot be null")
    private BigDecimal num2;
}

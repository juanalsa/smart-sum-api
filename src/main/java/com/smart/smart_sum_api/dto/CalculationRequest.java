package com.smart.smart_sum_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculationRequest {

    private BigDecimal num1;
    private BigDecimal num2;
}

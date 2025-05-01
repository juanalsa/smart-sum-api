package com.smart.smart_sum_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculationResponse {

    private final BigDecimal result;

    public CalculationResponse(BigDecimal result) {
        this.result = result;
    }
}

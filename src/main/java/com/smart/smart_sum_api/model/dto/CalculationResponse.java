package com.smart.smart_sum_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Response containing the result of the calculation")
@Data
public class CalculationResponse {

    @Schema(description = "Final result after applying the dynamic percentage", example = "33.0")
    private final BigDecimal result;

    public CalculationResponse(BigDecimal result) {
        this.result = result;
    }
}

package com.smart.smart_sum_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Request containing two numbers to be summed and modified by a dynamic percentage")
@Data
public class CalculationRequest {

    @Schema(description = "First number", example = "10.0", required = true)
    @NotNull(message = "num1 cannot be null")
    private BigDecimal num1;

    @Schema(description = "Second number", example = "20.0", required = true)
    @NotNull(message = "num2 cannot be null")
    private BigDecimal num2;
}

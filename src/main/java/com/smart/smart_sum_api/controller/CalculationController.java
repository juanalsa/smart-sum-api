package com.smart.smart_sum_api.controller;

import com.smart.smart_sum_api.model.dto.CalculationRequest;
import com.smart.smart_sum_api.model.dto.CalculationResponse;
import com.smart.smart_sum_api.service.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Calculation", description = "Calculation operations with dynamic percentage")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @Operation(summary = "Calculate sum with dynamic percentage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful calculation"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "503", description = "Percentage service not available")
    })
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody @Valid CalculationRequest request) {
        CalculationResponse response = calculationService.calculate(request);
        return ResponseEntity.ok(response);
    }
}

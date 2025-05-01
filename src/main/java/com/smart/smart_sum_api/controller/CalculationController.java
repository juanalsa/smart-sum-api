package com.smart.smart_sum_api.controller;

import com.smart.smart_sum_api.dto.CalculationRequest;
import com.smart.smart_sum_api.dto.CalculationResponse;
import com.smart.smart_sum_api.service.CalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody @Valid CalculationRequest request) {
        CalculationResponse response = calculationService.calculate(request);
        return ResponseEntity.ok(response);
    }
}

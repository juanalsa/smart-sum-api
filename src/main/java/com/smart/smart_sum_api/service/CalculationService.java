package com.smart.smart_sum_api.service;

import com.smart.smart_sum_api.dto.CalculationRequest;
import com.smart.smart_sum_api.dto.CalculationResponse;
import com.smart.smart_sum_api.entity.LogEntry;
import com.smart.smart_sum_api.event.LogEvent;
import com.smart.smart_sum_api.exception.PercentageServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final PercentageService percentageService;
    private final ApplicationEventPublisher eventPublisher;
    private BigDecimal cachedPercentage = null;

    public CalculationResponse calculate(CalculationRequest request) {
        BigDecimal percentage;
        BigDecimal result;
        LogEntry logEntry = new LogEntry();
        logEntry.setTimestamp(LocalDateTime.now());
        logEntry.setEndpoint("/calculate");
        logEntry.setParameters("num1=" + request.getNum1() + ", num2=" + request.getNum2());

        try {
            percentage = percentageService.getCurrentPercentage();
            cachedPercentage = percentage;
        } catch (Exception e) {
            if (cachedPercentage != null) {
                percentage = cachedPercentage;
            } else {
                logEntry.setError("Error=" + e.getMessage());
                eventPublisher.publishEvent(new LogEvent(this, logEntry));
                throw new PercentageServiceUnavailableException("Error no cached percentage available and external service failed");
            }
        }

        BigDecimal sum = request.getNum1().add(request.getNum2());
        result = sum.multiply(BigDecimal.ONE.add(percentage));
        logEntry.setResponse("result=" + result);
        eventPublisher.publishEvent(new LogEvent(this, logEntry));

        return new CalculationResponse(result);
    }
}

package com.smart.smart_sum_api.exception;

import org.springframework.http.HttpStatus;

public class PercentageServiceUnavailableException extends ApiException {

    public PercentageServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}

package com.smart.smart_sum_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidCalculationInputException extends ApiException {

    public InvalidCalculationInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

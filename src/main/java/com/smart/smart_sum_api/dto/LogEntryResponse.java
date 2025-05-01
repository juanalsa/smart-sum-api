package com.smart.smart_sum_api.dto;

import com.smart.smart_sum_api.entity.LogEntry;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogEntryResponse {

    private LocalDateTime timestamp;
    private String endpoint;
    private String parameters;
    private String response;
    private String error;

    public LogEntryResponse(LogEntry entity) {
        this.timestamp = entity.getTimestamp();
        this.endpoint = entity.getEndpoint();
        this.parameters = entity.getParameters();
        this.response = entity.getResponse();
        this.error = entity.getError();
    }
}

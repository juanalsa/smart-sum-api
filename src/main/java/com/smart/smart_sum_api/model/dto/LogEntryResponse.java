package com.smart.smart_sum_api.model.dto;

import com.smart.smart_sum_api.model.entity.LogEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Represents a log of an API request")
@Data
public class LogEntryResponse {
    @Schema(description = "Timestamp of the request")
    private LocalDateTime timestamp;

    @Schema(description = "Endpoint called", example = "/api/calculate")
    private String endpoint;

    @Schema(description = "Request parameters", example = "num1=10, num2=20")
    private String parameters;

    @Schema(description = "API response", example = "result=33.0")
    private String response;

    @Schema(description = "Error message if any", example = "Percentage service unavailable")
    private String error;

    public LogEntryResponse(LogEntry entity) {
        this.timestamp = entity.getTimestamp();
        this.endpoint = entity.getEndpoint();
        this.parameters = entity.getParameters();
        this.response = entity.getResponse();
        this.error = entity.getError();
    }
}

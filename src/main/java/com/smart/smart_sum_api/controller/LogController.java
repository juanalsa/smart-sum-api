package com.smart.smart_sum_api.controller;

import com.smart.smart_sum_api.model.dto.LogEntryResponse;
import com.smart.smart_sum_api.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Logs", description = "Operations for retrieving call history")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @Operation(summary = "Get paginated request logs", description = "Returns paginated log entries. Optional filter for errors only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping("/history")
    public ResponseEntity<Page<LogEntryResponse>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean onlyErrors
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<LogEntryResponse> response = logService.getHistory(pageable, onlyErrors);

        return ResponseEntity.ok(response);
    }
}

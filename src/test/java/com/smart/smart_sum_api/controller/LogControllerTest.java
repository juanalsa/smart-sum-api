package com.smart.smart_sum_api.controller;

import com.smart.smart_sum_api.dto.LogEntryResponse;
import com.smart.smart_sum_api.entity.LogEntry;
import com.smart.smart_sum_api.repository.LogEntryRepository;
import com.smart.smart_sum_api.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LogService logService;

    @MockitoBean
    private LogEntryRepository repository;

    private LogEntryResponse logEntryResp1;
    private LogEntryResponse logEntryResp2;

    @BeforeEach
    void setup() {
        LogEntry logEntry1 = new LogEntry();
        logEntry1.setTimestamp(LocalDateTime.now());
        logEntry1.setEndpoint("/calculate");
        logEntry1.setParameters("num1=1, num2=2");
        logEntry1.setResponse("result=3.0");

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setTimestamp(LocalDateTime.now());
        logEntry2.setEndpoint("/calculate");
        logEntry2.setParameters("num1=4, num2=5");
        logEntry2.setError("Simulated error");

        logEntryResp1 = new LogEntryResponse(logEntry1);
        logEntryResp2 = new LogEntryResponse(logEntry2);
    }

    @Test
    void should_return_all_logs() throws Exception {
        // Arrange
        Page<LogEntryResponse> mockPage = new PageImpl<>(List.of(logEntryResp1, logEntryResp2),
                PageRequest.of(0, 10), 2);

        when(logService.getHistory(any(PageRequest.class), eq(false))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(get("/api/history")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void should_return_only_error_logs_when_only_errors_true() throws Exception {
        // Arrange
        Page<LogEntryResponse> mockPage = new PageImpl<>(List.of(logEntryResp2),
                PageRequest.of(0, 10), 1);

        when(logService.getHistory(any(PageRequest.class), eq(true))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(get("/api/history")
                        .param("page", "0")
                        .param("size", "10")
                        .param("onlyErrors", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].error").exists());
    }
}
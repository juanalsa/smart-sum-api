package com.smart.smart_sum_api.service;

import com.smart.smart_sum_api.dto.LogEntryResponse;
import com.smart.smart_sum_api.entity.LogEntry;
import com.smart.smart_sum_api.repository.LogEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogEntryRepository repository;

    @InjectMocks
    private LogService logService;

    @Test
    void should_return_all_logs_when_only_errors_is_false() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<LogEntry> entries = List.of(new LogEntry(), new LogEntry());
        Page<LogEntry> page = new PageImpl<>(entries, pageable, entries.size());

        when(repository.findAll(pageable)).thenReturn(page);

        // Act
        Page<LogEntryResponse> result = logService.getHistory(pageable, false);

        // Assert
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void should_return_only_errors_when_only_errors_is_true() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<LogEntry> entries = List.of(new LogEntry());
        Page<LogEntry> page = new PageImpl<>(entries, pageable, entries.size());

        when(repository.findByErrorIsNotNull(pageable)).thenReturn(page);

        // Act
        Page<LogEntryResponse> result = logService.getHistory(pageable, true);

        // Assert
        assertEquals(1, result.getTotalElements());
    }
}
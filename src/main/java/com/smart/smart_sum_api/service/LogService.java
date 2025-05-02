package com.smart.smart_sum_api.service;

import com.smart.smart_sum_api.model.dto.LogEntryResponse;
import com.smart.smart_sum_api.model.entity.LogEntry;
import com.smart.smart_sum_api.repository.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogEntryRepository repository;

    public Page<LogEntryResponse> getHistory(Pageable pageable, boolean onlyErrors) {
        Page<LogEntry> page = onlyErrors
                ? repository.findByErrorIsNotNull(pageable)
                : repository.findAll(pageable);

        return page.map(LogEntryResponse::new);
    }
}

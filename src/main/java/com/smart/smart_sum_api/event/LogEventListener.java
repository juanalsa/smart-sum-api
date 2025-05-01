package com.smart.smart_sum_api.event;

import com.smart.smart_sum_api.repository.LogEntryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener {

    private final LogEntryRepository repository;

    public LogEventListener(LogEntryRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void handleLogEvent(LogEvent event) {
        // Save the log entry to the database
        repository.save(event.getLogEntry());
    }
}

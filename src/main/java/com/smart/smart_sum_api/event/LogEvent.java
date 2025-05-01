package com.smart.smart_sum_api.event;

import com.smart.smart_sum_api.entity.LogEntry;
import org.springframework.context.ApplicationEvent;

public class LogEvent extends ApplicationEvent {

    private final LogEntry logEntry;

    public LogEvent(Object source, LogEntry logEntry) {
        super(source);
        this.logEntry = logEntry;
    }

    public LogEntry getLogEntry() {
        return logEntry;
    }
}

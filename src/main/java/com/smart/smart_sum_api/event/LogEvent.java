package com.smart.smart_sum_api.event;

import com.smart.smart_sum_api.entity.LogEntry;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogEvent extends ApplicationEvent {

    private final LogEntry logEntry;

    public LogEvent(Object source, LogEntry logEntry) {
        super(source);
        this.logEntry = logEntry;
    }
}

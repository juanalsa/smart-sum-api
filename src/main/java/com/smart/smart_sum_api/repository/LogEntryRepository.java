package com.smart.smart_sum_api.repository;

import com.smart.smart_sum_api.model.entity.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

    Page<LogEntry> findByErrorIsNotNull(Pageable pageable);
}

package com.example.demo.common.log;

import com.example.demo.common.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Log> findByCategoryAndCreatedAtBetween(String category, LocalDateTime startDate, LocalDateTime endDate);

    List<Log> findByMessageContaining(String keyword);
}

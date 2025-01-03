package com.example.demo.common.log;

import com.example.demo.common.log.entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    // 로그 저장
    public void saveLog(Log.Action action, String message, String category, Long entityId) {
        Log log = Log.builder()
                .action(action)
                .message(message)
                .category(category)
                .entityId(entityId)
                .createdAt(LocalDateTime.now())
                .build();
        logRepository.save(log);
    }

    // 모든 로그 조회
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    // 날짜 범위로 로그 조회
    public List<Log> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return logRepository.findByCreatedAtBetween(startDate, endDate);
    }

    // 카테고리와 날짜로 로그 조회
    public List<Log> getLogsByCategoryAndDate(String category, LocalDateTime startDate, LocalDateTime endDate) {
        return logRepository.findByCategoryAndCreatedAtBetween(category, startDate, endDate);
    }

    // 메시지 키워드로 로그 조회
    public List<Log> searchLogsByKeyword(String keyword) {
        return logRepository.findByMessageContaining(keyword);
    }
}

package com.example.demo.common.log;

import com.example.demo.common.log.entity.Log;
import com.example.demo.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Log API", description = "로그 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/logs")
public class LogController {

    private final LogService logService;

    /**
     * 로그 저장 API (테스트용)
     * [POST] /app/logs
     * @return BaseResponse<String>
     */
    @Operation(summary = "로그 저장", description = "Create, Update, Delete 매서드에 대해 로그를 저장합니다.")
    @PostMapping("")
    public BaseResponse<String> saveLog(
            @RequestParam Log.Action action,
            @RequestParam String message,
            @RequestParam String category,
            @RequestParam String entityType,
            @RequestParam Long entityId) {
        logService.saveLog(action, message, category, entityType, entityId);
        return new BaseResponse<>("로그 저장 완료");
    }

    /**
     * 전체 로그 조회 API
     * [GET] /app/logs
     * @return BaseResponse<String>
     */
    @Operation(summary = "전체 로그 조회")
    @GetMapping("")
    public BaseResponse<List<Log>> getLogs() {
        List<Log> logs = logService.getAllLogs();
        return new BaseResponse<>(logs);
    }

    /**
     * 날짜 범위로 로그 조회 API
     * [GET] /app/logs/date
     * @return BaseResponse<List<Log>>
     */
    @Operation(summary = "날짜 범위로 로그 조회", description = "시작일과 종료일을 기준으로 로그를 조회합니다.")
    @GetMapping("/date")
    public BaseResponse<List<Log>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Log> logs = logService.getLogsByDateRange(startDate, endDate);
        return new BaseResponse<>(logs);
    }

    /**
     * 카테고리와 날짜 범위로 로그 조회 API
     * [GET] /app/logs/category
     * @return BaseResponse<List<Log>>
     */
    @Operation(summary = "카테고리와 날짜 범위로 로그 조회", description = "카테고리와 날짜 범위를 기준으로 로그를 조회합니다.")
    @GetMapping("/category")
    public BaseResponse<List<Log>> getLogsByCategoryAndDate(
            @RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Log> logs = logService.getLogsByCategoryAndDate(category, startDate, endDate);
        return new BaseResponse<>(logs);
    }

    /**
     * 특정 메시지 포함 로그 조회 API
     * [GET] /app/logs/search
     * @return BaseResponse<List<Log>>
     */
    @Operation(summary = "특정 메시지 포함 로그 조회", description = "특정 키워드를 포함하는 로그를 조회합니다.")
    @GetMapping("/search")
    public BaseResponse<List<Log>> searchLogsByKeyword(@RequestParam String keyword) {
        List<Log> logs = logService.searchLogsByKeyword(keyword);
        return new BaseResponse<>(logs);
    }
}

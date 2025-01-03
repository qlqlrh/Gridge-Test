package com.example.demo.common.log.aop;

import com.example.demo.common.log.LogService;
import com.example.demo.common.log.entity.Log;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogService logService;

    /**
     * createUser 메서드 실행 후 CREATE 로그 저장
     */
    @AfterReturning(value = "com.example.demo.common.log.aop.UserPointcuts.createUser()", returning = "returnValue")
    public void logAfterCreateUser(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof BaseResponse) {
            BaseResponse<?> response = (BaseResponse<?>) returnValue;
            if (response.getResult() instanceof PostUserRes) {
                PostUserRes postUserRes = (PostUserRes) response.getResult();
                Long userId = postUserRes.getId();

                // 로그 저장
                logService.saveLog(
                        Log.Action.CREATE,
                        "User created with ID: " + userId,
                        "User",
                        userId
                );
            }
        }
    }


    /**
     * modifyUserName 메서드 실행 후 UPDATE 로그 저장
     */
    @AfterReturning(value = "com.example.demo.common.log.aop.UserPointcuts.modifyUserName()", returning = "returnValue")
    public void logAfterModifyUserName(JoinPoint joinPoint, Object returnValue) {
        Object[] args = joinPoint.getArgs();
        Long entityId = (Long) args[0]; // 첫 번째 매개변수에서 User ID 추출
        String entityType = "User";
        PatchUserReq patchUserReq = (PatchUserReq) args[1]; // 두 번째 매개변수에서 수정된 값 추출

        // 변경된 이름 가져오기
        String updatedName = patchUserReq.getName();

        logService.saveLog(
                Log.Action.UPDATE,
                "User updated with ID: " + entityId + ", Updated Name: " + updatedName,
                entityType,
                entityId
        );
    }

    /**
     * updateConsent 메서드 실행 후 UPDATE 로그 저장
     */
    @AfterReturning(value = "com.example.demo.common.log.aop.UserPointcuts.updateConsent()", returning = "returnValue")
    public void logAfterUpdateConsent(JoinPoint joinPoint, Object returnValue) {
        Object[] args = joinPoint.getArgs();
        Long entityId = (Long) args[0]; // 첫 번째 매개변수는 User ID
        String entityType = "User"; // 엔티티 타입 설정

        logService.saveLog(
                Log.Action.UPDATE,
                "Consent updated for User with ID: " + entityId,
                entityType,
                entityId
        );
    }

    /**
     * deleteUser 메서드 실행 후 DELETE 로그 저장
     */
    @AfterReturning(value = "com.example.demo.common.log.aop.UserPointcuts.deleteUser()", returning = "returnValue")
    public void logAfterDeleteUser(JoinPoint joinPoint, Object returnValue) {
        Object[] args = joinPoint.getArgs();
        Long entityId = (Long) args[0];
        String entityType = "User";

        logService.saveLog(
                Log.Action.DELETE,
                "User deleted with ID: " + entityId,
                entityType,
                entityId
        );
    }
}

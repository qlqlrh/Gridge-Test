package com.example.demo.common.log.aop;

import org.aspectj.lang.annotation.Pointcut;

public class UserPointcuts {

    /**
     * createUser 메서드 포인트컷 (CREATE 로그)
     */
    @Pointcut("execution(* com.example.demo.src.user.UserController.createUser(..))")
    public void createUser() {
    }

    /**
     * modifyUserName 메서드 포인트컷 (UPDATE 로그)
     */
    @Pointcut("execution(* com.example.demo.src.user.UserController.modifyUserName(..))")
    public void modifyUserName() {
    }

    /**
     * updateConsent 메서드 포인트컷 (UPDATE 로그)
     */
    @Pointcut("execution(* com.example.demo.src.user.UserController.updateConsent(..))")
    public void updateConsent() {
    }

    /**
     * deleteUser 메서드 포인트컷 (DELETE 로그)
     */
    @Pointcut("execution(* com.example.demo.src.user.UserController.deleteUser(..))")
    public void deleteUser() {
    }
}

package com.example.demo.src.user.scheduler;

import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ConsentScheduler {

    private final UserRepository userRepository;

    // 매일 0시 0분 0초(자정)에 동의 갱신 필요한 사용자 검사
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkConsentForAllUsers() {
        List<User> users = userRepository.findAll();
        LocalDate today = LocalDate.now();

        for (User user : users) {
            if (user.getLastConsentDate().plusYears(1).isBefore(today)) {
                user.setConsentRequired(true);
                userRepository.save(user);
            }
        }
    }
}

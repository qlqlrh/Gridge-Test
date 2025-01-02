package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate birthday;

    private User.JoinType joinType;
    private String socialId; // LOCAL일 경우 null

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .password(this.password)
                .birthday(this.birthday)
                .joinType(this.joinType)
                .socialId(this.socialId) // 소셜 ID 설정
                .lastConsentDate(LocalDate.now()) // 현재 날짜를 마지막 약관 동의 날짜로 설정
                .consentRequired(false) // 동의 갱신 필요 여부 초기값은 false
                .build();
    }
}

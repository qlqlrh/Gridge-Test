package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "users") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class User extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_type", nullable = false, length = 20)
    private JoinType joinType;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "last_consent_date", nullable = false)
    private LocalDate lastConsentDate;

    @Column(name = "consent_required", nullable = false)
    private boolean consentRequired;

    @Builder
    public User(String name, String email, String phoneNumber, String password, LocalDate birthday, JoinType joinType, String socialId, LocalDate lastConsentDate, boolean consentRequired) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthday = birthday;
        this.joinType = joinType;
        this.socialId = socialId;
        this.lastConsentDate = lastConsentDate;
        this.consentRequired = consentRequired;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void deleteUser() {
        this.state = State.INACTIVE;
    }

    public void markAsDormant() {
        this.state = State.DORMANT;
    }

    public void blockUser() {
        this.state = State.BLOCKED;
    }

    public enum JoinType {
        LOCAL, GOOGLE, KAKAO, NAVER, APPLE
    }

}

package com.example.demo.common.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Action action;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = true, length = 50)
    private String entityType;

    @Column(nullable = true)
    private Long entityId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public Log(Action action, String message, String category, String entityType, Long entityId, LocalDateTime createdAt) {
        this.action = action;
        this.message = message;
        this.category = category;
        this.entityType = entityType;
        this.entityId = entityId;
        this.createdAt = createdAt;
    }

    public enum Action {
        CREATE, UPDATE, DELETE
    }
}

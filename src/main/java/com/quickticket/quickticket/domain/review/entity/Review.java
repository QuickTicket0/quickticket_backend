package com.quickticket.quickticket.domain.review.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long reviewId;

    @JoinColumn(nullable = false)
    private Event event;

    @JoinColumn(nullable = false)
    private User user;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Short userRating;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}

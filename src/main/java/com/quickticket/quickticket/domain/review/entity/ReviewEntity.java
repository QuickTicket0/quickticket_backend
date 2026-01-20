package com.quickticket.quickticket.domain.review.entity;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long reviewId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private EventEntity event;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private UserEntity user;

    @NotNull
    @Column(nullable = false)
    private Short userRating;

    @NotNull
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @Column(length = 3000, nullable = false)
    private String content;
}

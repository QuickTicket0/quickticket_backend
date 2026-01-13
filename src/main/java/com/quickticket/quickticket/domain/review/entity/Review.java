package com.quickticket.quickticket.domain.review.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(nullable = false)
    private Long reviewId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Event event;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private Short userRating;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(length = 3000, nullable = false)
    private String content;
}

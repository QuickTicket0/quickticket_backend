package com.quickticket.quickticket.domain.review.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor(onConstructor_ = {@Default})
public class Review {
    private Long id;
    private Event event;
    private User user;
    /// 0.5점부터 5.0점까지 5성 별점제로 합니다.
    private Float userRating;
    private LocalDateTime createdAt;
    private String content;
}
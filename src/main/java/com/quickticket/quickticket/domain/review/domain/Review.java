package com.quickticket.quickticket.domain.review.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Review {
    private Long id;
    private Event event;
    private User user;
    /// 0.5점부터 5.0점까지 5성 별점제로 합니다.
    private Float userRating;
    private LocalDateTime createdAt;
    private String content;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}
package com.quickticket.quickticket.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResponse {
    @Builder
    public record EventPageReviewListItem(
        Long id,
        String username,
        Float userRating,
        LocalDateTime createdAt,
        String content
    ) {}
}
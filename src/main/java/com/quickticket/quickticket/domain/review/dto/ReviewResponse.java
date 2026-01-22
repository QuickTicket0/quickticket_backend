package com.quickticket.quickticket.domain.review.dto;

import com.quickticket.quickticket.domain.review.entity.ReviewEntity;
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
    ) {
        public static EventPageReviewListItem from(ReviewEntity review) {
            return EventPageReviewListItem.builder()
                    .id(review.getReviewId())
                    .username(review.getUser().getUsername())
                    .userRating(review.getUserRating().floatValue() / 2)
                    .createdAt(review.getCreatedAt())
                    .content(review.getContent())
                    .build();
        }
    }
}
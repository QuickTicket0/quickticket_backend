package com.quickticket.quickticket.domain.review.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class ReviewRequest {
    @Builder
    public record Create(
        @NotNull
        @Min(0)
        Long eventId,

        @NotNull
        @Min(0)
        Short userRating,

        @NotBlank
        @Size(max = 1000)
        String content
    ) {}
}
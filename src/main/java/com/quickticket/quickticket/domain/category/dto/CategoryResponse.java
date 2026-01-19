package com.quickticket.quickticket.domain.category.dto;

import lombok.Builder;

public class CategoryResponse {
    @Builder
    public record Details(
        Long id,
        String name
    ) {}
}
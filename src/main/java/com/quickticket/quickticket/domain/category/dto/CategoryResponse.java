package com.quickticket.quickticket.domain.category.dto;

import lombok.Builder;

/// 카테고리는 아직 자체적인 응답 페이지가 생기는 경우는 없습니다
public class CategoryResponse {
    @Builder
    public record Details(
        Long id,
        String name
    ) {}
}
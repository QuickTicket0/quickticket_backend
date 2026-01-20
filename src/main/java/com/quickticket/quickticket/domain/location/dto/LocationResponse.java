package com.quickticket.quickticket.domain.location.dto;

import lombok.Builder;

/// Location도 이것만 자체적으로 보여주는 페이지는 아직 없습니다
public class LocationResponse {
    @Builder
    public record Details(
        Long id,
        String name,
        String zipNumber,
        String sido,
        String siGunGu,
        String eupMyun,
        String doroCode,
        String doroName,
        String phone
    ) {}
}
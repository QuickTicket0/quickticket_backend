package com.quickticket.quickticket.domain.location.dto;

import lombok.Builder;

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
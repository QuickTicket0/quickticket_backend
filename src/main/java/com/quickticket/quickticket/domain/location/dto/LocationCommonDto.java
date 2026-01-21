package com.quickticket.quickticket.domain.location.dto;

import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.shared.validators.NumberString;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LocationCommonDto(
    @NotNull
    @Min(0)
    Long id,
    
    @NotBlank
    String name,

    String zipNumber,

    String sido,

    String siGunGu,

    String eupMyun,

    String doroCode,

    String doroName,
    
    @NumberString
    String phone
) {
    public static LocationCommonDto from(LocationEntity location) {
        return LocationCommonDto.builder()
                .doroCode(location.getDoroCode())
                .doroName(location.getDoro())
                .eupMyun(location.getEupmyun())
                .id(location.getLocationId())
                .name(location.getLocationName())
                .phone(location.getPhone())
                .sido(location.getSido())
                .siGunGu(location.getSigungu())
                .zipNumber(location.getZipNumber())
                .build();
    }
}
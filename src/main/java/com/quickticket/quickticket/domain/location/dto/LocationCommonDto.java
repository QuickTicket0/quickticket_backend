package com.quickticket.quickticket.domain.location.dto;

import com.quickticket.quickticket.shared.constraints.NumberString;
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
) {}
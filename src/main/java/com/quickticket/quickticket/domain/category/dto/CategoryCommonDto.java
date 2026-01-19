package com.quickticket.quickticket.domain.category.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryCommonDto(
    @NotNull
    @Min(0)
    Long id,

    @NotBlank
    String name
) {}
package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import lombok.Builder;

@Builder
public record EventCache(
    Long id,
    String name,
    AgeRating ageRating,
    Long locationId
) {}

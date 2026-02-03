package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.event.domain.AgeRating;

public record EventCache(
    String name,
    AgeRating ageRating,
    Long locationId
) {}

package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import lombok.Builder;

import java.sql.Blob;

public class EventResponse {
    @Builder
    public record ListItem(
        String name,
        
        String description,

        String category1Name,

        String category2Name,

        AgeRating ageRating,

        Blob thumbnailImage
    ) {}

    @Builder
    public record Details(
        Long id,

        String name,

        String description,

        CategoryCommonDto category1,

        CategoryCommonDto category2,

        AgeRating ageRating,

        Blob thumbnailImage,

        String agentName,

        String hostName,

        String contactData,

        LocationCommonDto location
    ) {}
}

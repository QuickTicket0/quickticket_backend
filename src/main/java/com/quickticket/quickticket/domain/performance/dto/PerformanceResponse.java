package com.quickticket.quickticket.domain.performance.dto;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PerformanceResponse {
    @Builder
    public record ListItem(
        Long id,

        PerformanceEventInfo event,

        Integer nth,

        Integer targetSeatNumber,

        List<String> performersName,

        LocalDateTime ticketingStartsAt,

        LocalDateTime ticketingEndsAt,
        
        LocalDateTime performanceStartsAt,

        LocalTime runningTime
    ) {
        @Builder
        public record PerformanceEventInfo(
            Long id,

            String name,

            CategoryCommonDto category1,

            CategoryCommonDto category2,

            AgeRating ageRating,

            Blob thumbnailImage,

            LocationCommonDto location
        ) {}
    }
}

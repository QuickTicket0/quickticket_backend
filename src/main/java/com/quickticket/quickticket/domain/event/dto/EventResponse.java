package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponse {
    /// 어드민 대시보드중 Event가 리스트에 나열될때 필요한 간략한 정보들
    @Builder
    public record ListItem(
        String name,
        
        String description,

        String category1Name,

        String category2Name,

        AgeRating ageRating,

        String cast,   // 출연자

        Blob thumbnailImage
    ) {
        public static ListItem from(EventEntity entity) {
            return ListItem.builder()
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .category1Name(entity.getCategory1().getName())
                    .category2Name(entity.getCategory2().getName())
                    .ageRating(entity.getAgeRating())
//                    .cast(entity.per)
                    .thumbnailImage(entity.getThumbnailImage())
                    .build();
        }
    }

    public record ReviewInfo(
        Long id,
        String username,
        Float userRating,
        LocalDateTime createdAt,
        String content
    ) {}

    /// Event 하나의 상세 정보
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

        LocationCommonDto location,

        List<ReviewInfo> reviews
    ) {
        public static Details from(EventEntity event) {
            return Details.builder()
                    .id(event.getEventId())
                    .name(event.getName())
                    .description(event.getDescription())
                    .category1(CategoryCommonDto.from(event.getCategory1()))
                    .category2(CategoryCommonDto.from(event.getCategory2()))
                    .ageRating(event.getAgeRating())
                    .thumbnailImage(event.getThumbnailImage())
                    .agentName(event.getAgentName())
                    .hostName(event.getHostName())
                    .contactData(event.getContactData())
                    .location(LocationCommonDto.from(event.getLocation()))
                    .build();
        }
    }
}

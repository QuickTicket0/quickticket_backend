package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponse {
    /// 어드민 대시보드중 Event가 리스트에 나열될때 필요한 간략한 정보들
    @Builder
    public record ListItem(
        Long eventId,

        String name,
        
        String description,

        String category1Name,

        String category2Name,

        AgeRating ageRating,

        String cast,   // 출연자

        String locationName,   // 공연장 이름

        LocalDateTime performanceStartsAt //공연시작일자
    ) {
        public static ListItem from(EventEntity event, PerformanceEntity performance) {
            // 상세 주소
            String location = "장소 미정";
            if (event.getLocation() != null) {
                String sido = event.getLocation().getSido() != null ? event.getLocation().getSido() : "";
                String sigungu = event.getLocation().getSigungu() != null ? event.getLocation().getSigungu() : "";
                String doro = event.getLocation().getDoro() != null ? event.getLocation().getDoro() : "";
                String locationName = event.getLocation().getLocationName() != null ? event.getLocation().getLocationName() : "";

                location = sido + " " + sigungu + " " + doro + " " + locationName;
            }

            // 출연진 정보
            String castInfo = (performance.getPerformersName() != null) ? String.join(", ", performance.getPerformersName()) : "-";

            return ListItem.builder()
                    .eventId(event.getEventId())
                    .name(event.getName() + " - " + performance.getPerformanceNth() + "회차")
                    .description(event.getDescription())
                    .category1Name(event.getCategory1().getName())
                    .category2Name(event.getCategory2() != null ? event.getCategory2().getName() : "없음")
                    .ageRating(event.getAgeRating())
                    .locationName(location.trim())   // 상세 주소
                    .cast(castInfo)                  // 회차별 출연진
                    .performanceStartsAt(performance.getPerformanceStartsAt()) //공연시작일자
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
                    .agentName(event.getAgentName())
                    .hostName(event.getHostName())
                    .contactData(event.getContactData())
                    .location(LocationCommonDto.from(event.getLocation()))
                    .build();
        }
    }

    /**
     * 관리자 페이지 검색 필터 조건을 담는 DTO
     * @param startDate
     * @param endDate
     * @param category
     * @param keyword
     */
    public record SearchCondition(
            String startDate, // 검색 시작일
            String endDate,   // 검색 종료일
            String category,  // 카테고리
            String keyword    // 검색어
    ) {}
}

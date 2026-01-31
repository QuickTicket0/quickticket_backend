package com.quickticket.quickticket.domain.performance.controller;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceResponse;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    /**
     * 특정 Event의 회차 목록 조회
     */
    @GetMapping("/event/{eventId}")
    public List<PerformanceResponse.ListItem> getPerformancesByEvent(
            @PathVariable Long eventId
    ) {
        List<Performance> performances =
                performanceService.findPerformancesByEventId(eventId);

        return performances.stream()
                .map(this::toListItem)
                .toList();
    }

    /**
     * Domain → Response DTO 변환
     * (Mapper를 Service가 아니라 Controller에서 마무리)
     */
    private PerformanceResponse.ListItem toListItem(Performance performance) {
        return PerformanceResponse.ListItem.builder()
                .id(performance.getId())
                .nth(performance.getNth())
                .targetSeatNumber(performance.getTargetSeatNumber())
                .performersName(performance.getPerformersName())
                .ticketingStartsAt(performance.getTicketingStartsAt())
                .ticketingEndsAt(performance.getTicketingEndsAt())
                .performanceStartsAt(performance.getPerformanceStartsAt())
                .runningTime(performance.getRunningTime())
                .event(
                        PerformanceResponse.ListItem.EventInfo.builder()
                                .id(performance.getEvent().getId())
                                .name(performance.getEvent().getName())
                                .category1(performance.getEvent().getCategory1())
                                .category2(performance.getEvent().getCategory2())
                                .ageRating(performance.getEvent().getAgeRating())
                                .thumbnailImage(performance.getEvent().getThumbnailImage())
                                .location(performance.getEvent().getLocation())
                                .build()
                )
                .build();
    }
}

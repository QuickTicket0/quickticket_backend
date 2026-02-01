package com.quickticket.quickticket.domain.event.controller;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.service.EventService;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final PerformanceService performanceService;

    @GetMapping("/event/{eventId}")
    public String event(Model model, @PathVariable Long eventId) {

        /*

        =========================
        LocationCommonDto locationInfo = LocationCommonDto.builder()
                .id(1L)
                .name("수원실내체육관")
                .sido("경기")
                .siGunGu("수원시")
                .build();

        TicketResponse.Details.TicketEventInfo eventInfo =
                TicketResponse.Details.TicketEventInfo.builder()
                        .name("2025-2026 이창섭 단독 콘서트 〈EndAnd〉 - 수원")
                        .dateRange("2026.01.24 ~ 2026.01.25")
                        .cast("이창섭")
                        .ageRating("만 7세 이상 관람가")
                        .location(LocationCommonDto.builder()
                                .id(1L)
                                .name("수원실내체육관")
                                .build())
                        .build();
        */


        EventResponse.Details eventDetails =
                service.getResponseDetailsById(eventId);

        List<Performance> performances =
                performanceService.findPerformancesByEventId(eventId);

        model.addAttribute("event", eventDetails);
        model.addAttribute("performances", performances);

        return "event";
    }

    @GetMapping("/admin/event")
    public String adminEvent(Model model) {

        /*
        =========================
        더미 이벤트 목록 (참고용)
        =========================
        List<EventResponse.ListItem> eventList = List.of(
                EventResponse.ListItem.builder()
                        .name("2026 이창섭 단독 콘서트 〈EndAnd〉")
                        .description("수원 실내체육관")
                        .category1Name("CONCERT")
                        .category2Name("KPOP")
                        .cast("이창섭")
                        .ageRating(AgeRating.ALL)
                        .build(),
                EventResponse.ListItem.builder()
                        .name("2026 임영웅 아임 히어로")
                        .description("서울 월드컵경기장")
                        .category1Name("CONCERT")
                        .category2Name("TROT")
                        .cast("임영웅")
                        .ageRating(AgeRating.ALL)
                        .build()
        );
        */

        // =========================
        // 실제 구현 파트
        // =========================
        List<EventResponse.ListItem> eventList =
                service.getEventListForAdmin();

        model.addAttribute("eventList", eventList);

        return "admin/event";
    }

    @GetMapping("/editEvent/{eventId}")
    public String editEvent(Model model, @PathVariable Long eventId) {

        // 이벤트 수정 페이지용 상세 데이터
        EventResponse.Details eventDetails =
                service.getResponseDetailsById(eventId);

        model.addAttribute("event", eventDetails);

        return "admin/editEvent";
    }

    @GetMapping("/newEvent")
    public String newEvent(Model model) {
        return "admin/newEvent";
    }
}

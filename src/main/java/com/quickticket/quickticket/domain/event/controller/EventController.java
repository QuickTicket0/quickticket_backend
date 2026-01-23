package com.quickticket.quickticket.domain.event.controller;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {
    @GetMapping("/event")
    public String event(Model model) {
        /*
        LocationCommonDto locationInfo = LocationCommonDto.builder()
                .id(1L)
                .name("수원실내체육관")
                .sido("경기")
                .siGunGu("수원시")
                .build();

        // 공연 기본 정보 (EventInfo)
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

        // 회차 정보 (PerformanceInfo)
        TicketResponse.Details.TicketPerformanceInfo performanceInfo =
                TicketResponse.Details.TicketPerformanceInfo.builder()
                        .nth(1)
                        .performanceStartsAt(LocalDateTime.of(2026, 1, 24, 18, 0)) // 1회 18시
                        .runningTime(LocalTime.of(2, 0)) // 120분
                        .build();

        // 좌석 가격 정보 (SeatClasses)
        List<TicketResponse.Details.TicketSeatClassInfo> seatClasses = List.of(
                new TicketResponse.Details.TicketSeatClassInfo(1L, "VIP석", 154000L),
                new TicketResponse.Details.TicketSeatClassInfo(2L, "R석", 143000L),
                new TicketResponse.Details.TicketSeatClassInfo(3L, "S석", 132000L)
        );

        // 전체 DTO
        TicketResponse.Details ticketDto = TicketResponse.Details.builder()
                .id(null) // 상세페이지라 예매ID는 없음
                .event(eventInfo)
                .performance(performanceInfo)
                .seatClasses(seatClasses)
                .build();
        */
        // 모델에 담기
        //model.addAttribute("ticket", ticketDto);
        return "event";
    }

    @GetMapping("/admin/event")
    public String adminEvent(Model model) {
        /*
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
        //model.addAttribute("eventList", eventList);

        return "admin/event";
    }

    @GetMapping("/editEvent/{eventId}")
    public String editEvent(Model model, @PathVariable String eventId) {
        return "admin/editEvent";
    }

    @GetMapping("/newEvent")
    public String newEvent(Model model) {
        return "admin/newEvent";
    }
}

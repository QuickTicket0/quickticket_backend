package com.quickticket.quickticket.domain.event.controller;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.service.EventService;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 관리자 페이지의 공연 목록 및 검색 화면을 처리
     * 날짜범위, 카테고리, 키워드 검색 조건으로 조회하면 페이징 처리된 결과를 반환
     * @param startDate 검색 시작 날짜
     * @param endDate   검색 종료 날짜
     * @param category  공연 카테고리
     * @param keyword   공연명 검색어
     * @param pageable  페이징 정보 (페이지당 5개)
     * @param model     프론트로 데이터를 전달하기 위한 객체
     * @return 관리자 공연 관리 페이지 경로
     */
    @GetMapping("/admin/event")
    public String adminEvent(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            // 기본 사이즈를 5로 설정
            @PageableDefault(size = 5) Pageable pageable,
            Model model) {

        if (startDate != null && startDate.contains(" ~ ")) {
            String[] dates = startDate.split(" ~ ");
            startDate = dates[0]; // "2026.03.05"
            endDate = (dates.length > 1) ? dates[1] : startDate;
        }

        // 검색 조건을 DTO로 생성
        EventResponse.SearchCondition condition = new EventResponse.SearchCondition(startDate, endDate, category, keyword);

        // 서비스 호출 시 pageable을 함께 전달하고, 반환 타입을 Page로 받음
        Page<EventResponse.ListItem> eventPage = performanceService.getEventListForAdmin(condition, pageable);

        // 모델에 데이터 담기
        model.addAttribute("eventList", eventPage.getContent()); //실제 목록 데이터 (5개)
        model.addAttribute("page", eventPage);
        model.addAttribute("condition", condition);

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

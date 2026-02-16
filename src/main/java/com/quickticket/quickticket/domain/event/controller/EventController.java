package com.quickticket.quickticket.domain.event.controller;

import com.quickticket.quickticket.domain.event.dto.EventRequest;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.service.EventService;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceRequest;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import com.quickticket.quickticket.domain.seat.service.SeatService;
import com.quickticket.quickticket.shared.infra.aws.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final PerformanceService performanceService;
    private final SeatService seatService;
    private final S3Service s3Service;

    /**
     * 공연 ID를 기반으로 S3에서 썸네일을 조회
     * DB에 파일명을 저장하지 않기 때문에, 가능한 확장자들을 순차적으로 시도하여 파일을 찾음
     * @param eventId 공연 고유 ID
     * @return S3에서 찾은 이미지 (없을 경우 404)
     */
    @GetMapping("/api/images/event/{eventId}")
    public ResponseEntity<Resource> getPosterImage(@PathVariable Long eventId) {
        //  확장자 리스트
        String[] extensions = {".jpg", ".png", ".jpeg", ".gif", ""};

        for (String ext : extensions) {
            String s3Key = "images/event/" + eventId + ext;

            try {
                // S3에 해당 키가 있는지 확인하고 있으면 바로 반환
                return s3Service.getObjectResponseEntity(s3Key);
            } catch (Exception e) {
                // 파일이 없으면 다음 확장자로 넘어감
                continue;
            }
        }

        // 확장자 리스트에 없으면 404 반환
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/event/{eventId}")
    public String event(Model model, @PathVariable Long eventId) {
        EventResponse.Details eventDetails =
                eventService.getResponseDetailsById(eventId);

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
     * @return 관리자 콘서트 관리 페이지 경로
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
            startDate = dates[0];
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

    /**
     * 공연 등록 페이지를 호출
     * @param model 화면에 데이터를 전달하기 위한 Model 객체
     * @return 관리자 콘서트 등록 페이지 경로
     */
    @GetMapping("/newEvent")
    public String newEvent(Model model) {
        return "admin/newEvent";
    }

    /**
     * 새로운 콘서트 정보를 등록
     * 이벤트 기본 정보, 콘서트 일정, 좌석 등급 정보를 각각의 서비스에서 처리함
     * 또한 썸네일 이미지를 S3에 업로드
     * @param eventDto       콘서트명, 카테고리, 좌석 정보 등을 포함한 DTO
     * @param performanceDto 공연 날짜, 회차 정보를 포함한 DTO
     * @param file           사용자가 업로드한 썸네일 이미지 파일
     * @return               성공 시 이벤트 목록 페이지로 리다이렉트, 실패 시 에러 파라미터를 포함한 등록 페이지
     */
    @PostMapping(value = "/admin/newEvent", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String registerEvent(
            @RequestPart("event") EventRequest.Create eventDto,
            @RequestPart("performance") PerformanceRequest.Create performanceDto,
            @RequestPart(value = "thumbnailImageFile", required = false) MultipartFile file) {

        String threadName = Thread.currentThread().getName();

        try {
            // event 저장
            Long savedEventId = eventService.saveEvent(eventDto, file);

            // performance 저장
            performanceService.registPerformance(savedEventId, performanceDto);

            // seatClass 저장
            if (eventDto.seatGrades() != null && !eventDto.seatGrades().isEmpty()) {
                seatService.saveSeatClasses(savedEventId, eventDto.seatGrades());
            }

            return "redirect:/admin/event";
        } catch (Exception e) {
            return "redirect:/admin/newEvent?error";
        }
    }

    /**
     * 공연 수정 페이지를 호출
     * @param model 화면에 데이터를 전달하기 위한 Model 객체
     * @return 관리자 콘서트 수정 페이지 경로
     */
    @GetMapping("/editEvent/{eventId}")
    public String editEvent(Model model, @PathVariable Long eventId) {

        // 이벤트 수정 페이지용 상세 데이터
        EventResponse.Details eventDetails =
                eventService.getResponseDetailsById(eventId);

        model.addAttribute("event", eventDetails);

        return "admin/editEvent";
    }
}

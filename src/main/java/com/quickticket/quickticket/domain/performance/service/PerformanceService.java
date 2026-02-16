package com.quickticket.quickticket.domain.performance.service;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceRequest;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final EventRepository eventRepository;
    private final PerformanceMapper performanceMapper;

    public List<Performance> findPerformancesByEventId(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new DomainException(PerformanceErrorCode.NOT_FOUND));

        return performanceRepository
                .findAllByEvent_EventId(eventId)
                .stream()
                .map(e ->
                        performanceMapper.toDomain(e, this.getWaitingLengthOfPerformance(e.getPerformanceId())))
                .toList();
    }

    public Performance getDomainById(Long performanceId) {
        var performanceEntity = performanceRepository.getReferenceById(performanceId);

        return performanceMapper.toDomain(performanceEntity, this.getWaitingLengthOfPerformance(performanceId));
    }

    @Transactional
    public Performance saveDomain(Performance domain) {
        return performanceRepository.saveDomain(domain);
    }

    public Long getWaitingLengthOfPerformance(Long performanceId) {
        return performanceRepository.getWaitingLengthOfPerformance(performanceId);
    }

    /**
     * 관리자 페이지의 검색 조건과 페이징 설정을 적용하여 공연 회차 리스트를 조회
     * @param condition 검색 필터 조건 (시작일, 종료일, 카테고리, 검색어)
     * @param pageable 페이징 설정 (페이지 번호, 한 페이지당 개수 등)
     * @return 검색 조건이 적용된 DTO 페이지 객체
     */
    public Page<EventResponse.ListItem> getEventListForAdmin(EventResponse.SearchCondition condition, Pageable pageable) {
        Page<PerformanceEntity> performancePage = performanceRepository.searchPerformances(condition, pageable);

        return performancePage.map(p -> EventResponse.ListItem.from(p.getEvent(), p));
    }

    /**
     * 특정 콘서트(Event)에 대한 회차(날짜, 시간 등) 정보를 등록
     * 전달받은 eventId로 해당 이벤트를 찾아 회차 정보와 맞으면 저장
     * @param eventId        회차를 등록할 대상 콘서트 ID
     * @param performanceDto 등록할 회차 정보(날짜, 시간, 회차 번호 등)가 담긴 데이터 객체
     * @throws DomainException 공연 정보를 찾을 수 없을 경우 NOT_FOUND 에러 발생
     */
    @Transactional
    public void registPerformance(Long eventId, PerformanceRequest.Create performanceDto) {
        var eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new DomainException(PerformanceErrorCode.NOT_FOUND));

        PerformanceEntity performanceEntity = performanceMapper.toEntity(performanceDto, eventEntity);

        // 저장
        performanceRepository.save(performanceEntity);
    }
}

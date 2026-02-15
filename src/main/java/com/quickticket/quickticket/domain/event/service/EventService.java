package com.quickticket.quickticket.domain.event.service;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository repository;
    private final PerformanceRepository performanceRepository;
    private final EventMapper mapper;
    
    public List<Event> getHotEventsTopN(int topN) {
        return repository.getAllByOrderByViewsDesc(PageRequest.of(0, topN)).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public EventResponse.Details getResponseDetailsById(Long id) {
        return EventResponse.Details.from(
                repository.getEntityByEventId(id)
                    .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND))
        );
    }

    public EventResponse.ListItem getResponseListItemById(Long id) {
        EventEntity event = repository.getEntityByEventId(id)
                .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND));
        PerformanceEntity performance = performanceRepository.findAll().stream()
                .filter(p -> p.getEvent().getEventId().equals(id))
                .findFirst()
                .orElse(null);

        return EventResponse.ListItem.from(event, performance);
    }

    /**
     * 관리자 페이지용 공연 목록을 조회
     * DB에 저장된 모든 공연(Performance) 데이터를 가져와 관리자용 DTO 리스트로 변환하여 반환
     * @return 관리자페이지의 공연 목록 리스트 (EventResponse.ListItem)
     */
    public List<EventResponse.ListItem> getEventListForAdmin() {
        return performanceRepository.findAll().stream()
                .map(performance -> EventResponse.ListItem.from(performance.getEvent(), performance))
                .toList();
    }
}

package com.quickticket.quickticket.domain.performance.service;

import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final EventRepository eventRepository;
    private final PerformanceMapper performanceMapper;

    public List<Performance> findPerformancesByEventId(Long eventId) {
        // ✅ 이벤트 존재 검증 (다른 도메인 스타일과도 잘 맞음)
        eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event not found. id=" + eventId));

        return performanceRepository
                .findAllByEvent_EventId(eventId)
                .stream()
                .map(performanceMapper::toDomain)
                .collect(Collector.toList());
    }
}

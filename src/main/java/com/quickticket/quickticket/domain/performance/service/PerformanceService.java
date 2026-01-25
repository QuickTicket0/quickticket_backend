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
        eventRepository.findById(eventId)
                .orElseThrow(() -> new DomainException(PerformanceErrorCode.NOT_FOUND));

        return performanceRepository
                .findAllByEvent_EventId(eventId)
                .stream()
                .map(performanceMapper::toDomain)
                .toList();
    }
}

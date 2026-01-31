package com.quickticket.quickticket.domain.performance.service;

import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import jakarta.transaction.Transactional;
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
    private final PerformanceRoundRepository roundRepository;

    public List<Performance> findPerformancesByEventId(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new DomainException(PerformanceErrorCode.NOT_FOUND));

        return performanceRepository
                .findAllByEvent_EventId(eventId)
                .stream()
                .map(performanceMapper::toDomain)
                .toList();
    }
    @Transactional
    public void createNextRounds() {
        // DB 조회 → 중복 생성 방지 → 저장
    }

    @Transactional
    public void updateRoundStatusByTime() {
        // 현재 시간 기준으로 OPEN/CLOSED/END 상태 변경
    }
}

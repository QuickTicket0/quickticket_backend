package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceCache;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceRepositoryCustom {
    @Cacheable(value = "cache:performance-cache", key = "#performanceId")
    PerformanceCache getCacheById(Long performanceId);

    @Cacheable(value = "stat:performance-ticket-waiting-length", key = "#performanceId")
    Long getWaitingLengthOfPerformance(Long performanceId);

    // 관리자 페이지 검색 필터링 메서드
    Page<PerformanceEntity> searchPerformances(EventResponse.SearchCondition condition, Pageable pageable);

    Performance saveDomain(Performance domain);
}

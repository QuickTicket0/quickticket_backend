package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PerformanceRepository
        extends JpaRepository<PerformanceEntity, Long>, PerformanceRepositoryCustom {
    List<PerformanceEntity> findAllByEvent_EventId(Long eventId);
}

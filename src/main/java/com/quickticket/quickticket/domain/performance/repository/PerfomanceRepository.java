package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {

    List<PerformanceEntity>
    findAllByEvent_EventIdOrderByPerformanceStartsAtAscPerformanceNthAsc(Long eventId);
}

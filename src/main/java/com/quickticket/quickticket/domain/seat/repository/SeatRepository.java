package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SeatRepository
        extends JpaRepository<SeatEntity, SeatId>, SeatRepositoryCustom {
    List<SeatEntity> getByPerformance_PerformanceId(Long performanceId);
}

package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatClassId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatClassRepository extends JpaRepository<SeatClassEntity, SeatClassId> {
    List<SeatClassEntity> getByEvent_EventId(Long eventId);
}

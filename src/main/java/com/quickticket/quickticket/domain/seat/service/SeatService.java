package com.quickticket.quickticket.domain.seat.service;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatArea;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.mapper.SeatAreaMapper;
import com.quickticket.quickticket.domain.seat.mapper.SeatClassMapper;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.seat.repository.SeatAreaRepository;
import com.quickticket.quickticket.domain.seat.repository.SeatClassRepository;
import com.quickticket.quickticket.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatClassRepository seatClassRepository;
    private final SeatAreaRepository seatAreaRepository;
    private final SeatMapper seatMapper;
    private final SeatClassMapper seatClassMapper;
    private final SeatAreaMapper seatAreaMapper;

    public Map<Long, SeatClass> getSeatClassesByEventId(Long eventId) {
        return seatClassRepository.getByEvent_EventId(eventId).stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatClassId(),
                        seatClassMapper::toDomain
                ));
    }

    public Map<Long, Seat> getSeatsByEventId(Long eventId) {
        return seatRepository.getByEvent_EventId(eventId).stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatId(),
                        seatMapper::toDomain
                ));
    }
}

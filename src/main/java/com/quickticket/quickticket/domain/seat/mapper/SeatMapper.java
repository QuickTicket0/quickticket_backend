package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        PerformanceMapper.class,
        SeatClassMapper.class,
        SeatAreaMapper.class
})
public interface SeatMapper {
    @Mapping(target = "id", source = "id.seatId")
    Seat toDomain(SeatEntity entity);
}

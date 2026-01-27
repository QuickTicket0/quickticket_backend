package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        PerformanceMapper.class,
        SeatClassMapper.class,
        SeatAreaMapper.class
    }
)
public interface SeatMapper {
    @Mapping(target = "id", source = "id.seatId")
    Seat toDomain(SeatEntity entity);

    @Mapping(target = "id", expression = "java(seatIdToEntity(domain))")
    SeatEntity toEntity(Seat domain);

    default SeatId seatIdToEntity(Seat domain) {
        return new SeatId(domain.getId(), domain.getPerformance().getId());
    }
}

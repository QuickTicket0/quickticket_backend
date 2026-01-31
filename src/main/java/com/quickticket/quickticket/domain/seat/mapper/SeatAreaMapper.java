package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatArea;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaId;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        EventMapper.class
    }
)
public interface SeatAreaMapper {
    @Mapping(target = "id", source = "id.seatAreaId")
    SeatArea toDomain(SeatAreaEntity entity);

    @Mapping(target = "id", expression = "java(seatAreaIdToEntity(domain))")
    SeatAreaEntity toEntity(SeatArea domain);

    default SeatAreaId seatAreaIdToEntity(SeatArea domain) {
        return new SeatAreaId(domain.getId(), domain.getEvent().getId());
    }
}

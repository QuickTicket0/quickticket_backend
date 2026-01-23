package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.seat.domain.SeatArea;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        EventMapper.class
})
public interface SeatAreaMapper {
    @Mapping(target = "id", source = "id.seatAreaId")
    SeatArea toDomain(SeatAreaEntity entity);
}

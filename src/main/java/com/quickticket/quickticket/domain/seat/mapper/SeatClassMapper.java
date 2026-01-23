package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
        EventMapper.class
})
public interface SeatClassMapper {
    @Mapping(target = "id", source = "id.seatClassId")
    SeatClass toDomain(SeatClassEntity entity);
}

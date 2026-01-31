package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.seat.domain.SeatArea;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaId;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatClassId;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        EventMapper.class
    }
)
public interface SeatClassMapper {
    @Mapping(target = "id", source = "id.seatClassId")
    SeatClass toDomain(SeatClassEntity entity);

    @Mapping(target = "id", expression = "java(seatClassIdToEntity(domain))")
    SeatClassEntity toEntity(SeatClass domain);

    default SeatClassId seatClassIdToEntity(SeatClass domain) {
        return new SeatClassId(domain.getId(), domain.getEvent().getId());
    }
}

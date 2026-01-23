package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatResponseMapper {
    @Mapping(target = "area", source = "seatArea.name")
    @Mapping(target = "areaId", source = "seatArea.id")
    @Mapping(target = "seatClass", source = "seatClass.name")
    @Mapping(target = "seatClassId", source = "seatClass.id")
    @Mapping(target = "price", source = "seatClass.price")
    TicketResponse.SeatInfo toResponse(Seat domain);
}


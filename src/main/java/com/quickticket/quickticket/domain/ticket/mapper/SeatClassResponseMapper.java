package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatClassResponseMapper {
    TicketResponse.SeatClassInfo toResponse(SeatClass domain);
}

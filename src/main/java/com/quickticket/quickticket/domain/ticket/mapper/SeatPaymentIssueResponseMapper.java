package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatPaymentIssueResponseMapper {
    @Mapping(target = "paidSeatId", source = "seat.id")
    TicketResponse.SeatPaymentInfo toResponse(SeatPaymentIssue domain);
}

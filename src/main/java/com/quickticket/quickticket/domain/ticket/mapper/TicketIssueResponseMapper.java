package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

@Mapper(
    componentModel = "spring"
)
public interface TicketIssueResponseMapper {
    @Mapping(target = "event", source = "ticket.performance.event")
    TicketResponse.Details toDetails(
            Ticket ticket,
            Map<Long, Seat> seats,
            List<SeatClass> seatClasses,
            List<SeatPaymentIssue> seatPayments,
            List<Long> wantingSeatsId
    );

    default TicketResponse.EventInfo toResponse(EventEntity entity) {
        return TicketResponse.EventInfo.builder()
                .ageRating(entity.getAgeRating())
//                .casting(entity)
                .location(LocationCommonDto.from(entity.getLocation()))
                .name(entity.getName())
//                .range()
                .build();
    };

    @Mapping(target = "nth", source = "performanceNth")
    TicketResponse.PerformanceInfo toResponse(PerformanceEntity entity);

    TicketResponse.SeatClassInfo toResponse(SeatClass domain);

    @Mapping(target = "paidSeatId", source = "seat.id")
    TicketResponse.SeatPaymentInfo toResponse(SeatPaymentIssue domain);

    @Mapping(target = "area", source = "seatArea.name")
    @Mapping(target = "areaId", source = "seatArea.id")
    @Mapping(target = "seatClass", source = "seatClass.name")
    @Mapping(target = "seatClassId", source = "seatClass.id")
    @Mapping(target = "price", source = "seatClass.price")
    TicketResponse.SeatInfo toResponse(Seat domain);
}

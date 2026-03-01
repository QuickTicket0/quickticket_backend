package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

@Mapper(
    componentModel = "spring",
    imports = { PaymentMethodCommonDto.class }
)
public interface TicketIssueResponseMapper {
    @Mapping(target = "event.id", source = "ticket.performance.event.id")
    @Mapping(target = "event.name", source = "ticket.performance.event.name")
    @Mapping(target = "performance.id", source = "ticket.performance.id")
    @Mapping(target = "performance.nth", source = "ticket.performance.nth")
    @Mapping(target = "paymentMethod", expression = "java(PaymentMethodCommonDto.from(ticket.getPaymentMethod()))")
    TicketResponse.Details toDetails(
            Ticket ticket,
            Map<Long, Seat> seats,
            List<SeatClass> seatClasses,
            List<SeatPaymentIssue> seatPayments,
            List<Long> wantingSeatsId
    );

    default PaymentMethodCommonDto toPaymentMethodDto(PaymentMethodEntity entity) {
        if (entity == null) return null;
        return PaymentMethodCommonDto.from(entity);
    }

    default TicketResponse.EventInfo toResponse(EventEntity entity) {
        if (entity == null) return null;

        return TicketResponse.EventInfo.builder()
                .id(entity.getEventId())
                .ageRating(entity.getAgeRating())
//                .casting(entity)
                .location(LocationCommonDto.from(entity.getLocation()))
                .name(entity.getName())
//                .range()
                .build();
    };

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nth", source = "nth")
    TicketResponse.PerformanceInfo toResponse(Performance domain);

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

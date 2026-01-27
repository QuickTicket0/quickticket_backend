package com.quickticket.quickticket.domain.payment.seatPayment.mapper;

import com.quickticket.quickticket.domain.payment.method.mapper.PaymentMethodMapper;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.entity.SeatPaymentIssueEntity;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        SeatMapper.class,
        TicketIssueMapper.class,
        UserMapper.class,
        SeatMapper.class,
        PaymentMethodMapper.class
    }
)
public interface SeatPaymentIssueMapper {
    @Mapping(target = "id", source = "paymentId")
    SeatPaymentIssue toDomain(SeatPaymentIssueEntity entity);

    @Mapping(target = "paymentId", source = "id")
    SeatPaymentIssueEntity toEntity(SeatPaymentIssue domain);
}

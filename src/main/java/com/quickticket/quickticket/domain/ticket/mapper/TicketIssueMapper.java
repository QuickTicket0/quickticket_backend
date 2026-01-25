package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.payment.method.mapper.PaymentMethodMapper;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PerformanceMapper.class,
        UserMapper.class,
        PaymentMethodMapper.class,
        SeatMapper.class
})
public interface TicketIssueMapper {
    @Mapping(target = "id", source = "entity.ticketIssueId")
    @Mapping(target = "wantingSeats", source = "wantingSeatEntities", qualifiedByName = "wantingSeatEntitiesToDomainMap")
    Ticket toDomain(TicketIssueEntity entity, List<SeatEntity> wantingSeatEntities);

    @Named("wantingSeatEntitiesToDomainMap")
    default Map<Long, Seat> wantingSeatEntitiesToDomainMap(List<SeatEntity> entities) {
        return entities.stream()
                .collect(Collectors.toMap(
                        e -> e.getSeatId(),
                        SeatMapper::toDomain
                ));
    }
}

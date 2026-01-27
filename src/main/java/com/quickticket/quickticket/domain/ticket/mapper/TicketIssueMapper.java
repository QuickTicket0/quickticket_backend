package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.payment.method.mapper.PaymentMethodMapper;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        
    @Mapping(target = "ticketIssueId", source = "id")
    TicketIssueEntity toEntity(Ticket domain);
        
    default List<SeatEntity> wantingSeatsToEntity(Ticket domain) {
        return domain.getWantingSeats().entrySet().stream()
                .map(e -> e.getValue())
                .map(SeatMapper::toEntity)
                .toList();
    }
}

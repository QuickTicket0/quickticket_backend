package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.payment.method.mapper.PaymentMethodMapper;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.redis.TicketBulkInsertQueueEntity;
import com.quickticket.quickticket.domain.ticket.entity.jpa.TicketIssueEntity;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        PerformanceMapper.class,
        UserMapper.class,
        PaymentMethodMapper.class,
        SeatMapper.class
    }
)

public abstract class TicketIssueMapper {
    @Autowired
    protected SeatMapper seatMapper;

    @Mapping(target = "id", source = "entity.ticketIssueId")
    @Mapping(target = "performance", source = "entity.performance")
    @Mapping(target = "paymentMethod", source = "entity.paymentMethod")
    @Mapping(target = "wantingSeats", source = "wantingSeatEntities", qualifiedByName = "wantingSeatEntitiesToDomainMap")
    public abstract Ticket toDomain(TicketIssueEntity entity, List<SeatEntity> wantingSeatEntities);


    @Mapping(target = "id", source = "entity.ticketIssueId")
    @Mapping(target = "performance", source = "performance")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "wantingSeats", source = "wantingSeatEntities", qualifiedByName = "wantingSeatEntitiesToDomainMap")
    public abstract Ticket toDomain(
            TicketBulkInsertQueueEntity entity,
            PerformanceEntity performance,
            UserEntity user,
            PaymentMethodEntity paymentMethod,
            List<SeatEntity> wantingSeatEntities
    );

    @Named("wantingSeatEntitiesToDomainMap")
    public Map<Long, Seat> wantingSeatEntitiesToDomainMap(List<SeatEntity> entities) {
        return entities.stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatId(),
                        seatMapper::toDomain
                ));
    }

    @Mapping(target = "ticketIssueId", source = "id")
    public abstract TicketIssueEntity toEntity(Ticket domain);

    @Mapping(target = "ticketIssueId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "performanceId", source = "performance.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "wantingSeatsId", expression = "java(domain.getWantingSeats().keySet().stream().toList())")
    public abstract TicketBulkInsertQueueEntity toBulkQueueEntity(Ticket domain);
        
    public List<SeatEntity> wantingSeatsToEntity(Ticket domain) {
        return domain.getWantingSeats().entrySet().stream()
                .map(e -> e.getValue())
                .map(seatMapper::toEntity)
                .toList();
    }
}

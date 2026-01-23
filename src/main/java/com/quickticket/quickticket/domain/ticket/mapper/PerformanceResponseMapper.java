package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PerformanceResponseMapper {
    @Mapping(target = "nth", source = "performanceNth")
    TicketResponse.PerformanceInfo toResponse(PerformanceEntity entity);
}
